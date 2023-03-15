package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.CachedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.PaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class manages the CachedPaperRepository. To ensure convenient response-times the CachedPaperRepository saves
 * CachedPapers that each represent either a reference or a citation to paper that has been added to a research.
 * Therefore, it is possible to quickly load all references and citations from a list of papers instead of having
 * to make a comparably slow api-call for each paper in the list
 *
 * @author ukgcc
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {
    private final CachedPaperRepository cachedPaperRepository;
    private final SavedPaperRepository savedPaperRepository;
    private final ApiActionController apiActionController;
    private final PaperRepository paperRepository;
    public static final ExecutorService cacheThread = Executors.newFixedThreadPool(1);

    /**
     * This method is called everytime the user opens a new research.
     * It deletes all CachedPapers that are currently stored in the CachedPaperRepository and belong to the
     * same user who opened the new research.
     * Then it creates a new CachedPaper for every reference and citation of papers saved to the new research and
     * saves them in the CachedPaperRepository
     *
     * @param research the research whose references and citations are to be cached
     */
    public void initializeCache(Research research) {
        log.debug("Queue cache initialize");
        cacheThread.submit(() -> {
            log.debug("Start cache initialize for research '" + research.getId() + "'");

            //empty the cache for the user
            List<CachedPaper> userCache = cachedPaperRepository.findByCachedPaperIdResearchUser(research.getUser());
            cachedPaperRepository.deleteAllInBatch(userCache);

            //load the cache for this research
            List<SavedPaper> savedPapers = savedPaperRepository.findBySavedPaperIdResearch(research);//Todo: only get added papers
            for (SavedPaper savedPaper : savedPapers) {
                try {
                    List<Paper> citations = apiActionController.getCitations(savedPaper.getSavedPaperId().getPaper());
                    List<Paper> references = apiActionController.getReferences(savedPaper.getSavedPaperId().getPaper());

                    paperRepository.saveAll(citations);
                    paperRepository.saveAll(references);

                    for (Paper paper : citations) {
                        createCachedPaper(research, paper, savedPaper.getSavedPaperId().getPaper(), CachedPaperType.CITATION);
                    }
                    for (Paper paper : references) {
                        createCachedPaper(research, paper, savedPaper.getSavedPaperId().getPaper(), CachedPaperType.REFERENCE);
                    }
                } catch (IOException e) {
                    log.error("Error while initializing cache for research '" + research.getId() + "'", e);
                }
            }

            log.debug("Finished cache initialize for research '" + research.getId() + "'");
        });
    }

    /**
     * Deletes all CachedPapers that belong to a certain research
     *
     * @param research the research whose CachedPapers are to be deleted
     */
    public void flushCacheResearch(Research research) {
        List<CachedPaper> cache = cachedPaperRepository.findByCachedPaperIdResearch(research);
        cachedPaperRepository.deleteAllInBatch(cache);
    }

    /**
     * Creates a new CachedPaper and saves it to the CachedPaperRepository. When creating many CachedPapers at once
     * use @link {@link #createUnsavedCachedPaper(Research, Paper, Paper, CachedPaperType)} to avoid unnecessary
     * database calls. Use {@link #saveCachedPapers(List)} to save the CachedPapers to the CachedPaperRepository
     * afterwards.
     *
     * @param research        the research the CachedPaper belongs to
     * @param paper           the paper the CachedPaper points to
     * @param parent          the parentPaper of the CachedPaper
     * @param cachedPaperType the type of the CachedPaper
     * @return the CachedPaper
     */
    public CachedPaper createCachedPaper(Research research, Paper paper, Paper parent, CachedPaperType cachedPaperType) {
        CachedPaper cachedPaper = new CachedPaper(paper, parent, research, cachedPaperType);
        cachedPaperRepository.save(cachedPaper);
        return cachedPaper;
        //cachedPaper isn´t save in research.cachedPapers to avoid duplicates
    }

    /**
     * Create a new CachedPaper without saving it to the CachedPaperRepository. This should be used when creating many
     * CachedPapers at once to avoid unnecessary database calls. Use {@link #saveCachedPapers(List)} to save the
     * CachedPapers to the CachedPaperRepository afterwards.
     *
     * @param research        the research the CachedPaper belongs to
     * @param paper           the paper the CachedPaper points to
     * @param parent          the parentPaper of the CachedPaper
     * @param cachedPaperType the type of the CachedPaper
     * @return the CachedPaper
     */
    public CachedPaper createUnsavedCachedPaper(Research research, Paper paper, Paper parent, CachedPaperType cachedPaperType) {
        return new CachedPaper(paper, parent, research, cachedPaperType);
    }

    /**
     * Saves a list of CachedPapers to the CachedPaperRepository.
     *
     * @param cachedPapers the CachedPapers to be saved
     */
    public void saveCachedPapers(List<CachedPaper> cachedPapers) {
        cachedPaperRepository.saveAll(cachedPapers);
    }

    /**
     * Deletes a cachedPaper from the CachedPaperRepository. Also checks if the CachedPapers paper and parentPaper
     * can be deleted as they now potentially have no Cached- or SavedPaper that points to them
     *
     * @param paper    the paper the CachedPaper points to
     * @param research the research the CachedPaper belongs to
     * @throws NotInDataBaseException when there is no CachedPaper to be deleted
     */
    public void deleteCachedPaper(Paper paper, Research research) throws NotInDataBaseException {
        //Todo: this could delete more than one Paper
        List<CachedPaper> cachedPaper = cachedPaperRepository.deleteByCachedPaperIdResearchAndCachedPaperIdPaper(research, paper);

        if (cachedPaper.isEmpty()) {
            throw new NotInDataBaseException("Cannot delete cachedPaper since it doesn't exist");
        }
        Paper parentPaper = cachedPaper.get(0).getCachedPaperId().getParentPaper();

        //deletes the parentPaper from the PaperRepository as well if no other Saved- or CachedPaper points to it
        if (savedPaperRepository.countBySavedPaperIdPaper(parentPaper) == 0 &&
                cachedPaperRepository.countByCachedPaperIdPaper(parentPaper) == 0 &&
                cachedPaperRepository.countByCachedPaperIdParentPaper(parentPaper) == 0) {
            paperRepository.delete(parentPaper);
        }

        //deletes the paper from the PaperRepository as well if no other Saved- or CachedPaper points to it
        if (savedPaperRepository.countBySavedPaperIdPaper(paper) == 0 &&
                cachedPaperRepository.countByCachedPaperIdPaper(paper) == 0 &&
                cachedPaperRepository.countByCachedPaperIdParentPaper(paper) == 0) {
            paperRepository.delete(paper);
        }
    }

    /**
     * If an added paper gets removed from a research we want to remove all related CachedPapers from the
     * CachedPaperRepository. So we find all CachedPapers that have the removed paper as parentPaper and delete
     * them if they belong to the same research as the removed paper
     *
     * @param paper    the paper got removed
     * @param research the research the paper got removed from
     */
    public void removeRelatedCachedPapers(Paper paper, Research research) {
        List<CachedPaper> cachedPapers = cachedPaperRepository.findByCachedPaperIdParentPaper(paper);
        for (CachedPaper cachedPaper : cachedPapers) {
            if (cachedPaper.getCachedPaperId().getResearch().equals(research)) {
                try {
                    deleteCachedPaper(paper, research);
                } catch (NotInDataBaseException ignored) {
                }
            }
        }
    }

    /**
     * Finds all references from a list of papers that belong to a research
     *
     * @param research the research the references belong to
     * @param papers   the list of papers whose references are to be found
     * @return the references
     */
    public List<CachedPaper> getCachedReferences(Research research, List<Paper> papers) {
        List<CachedPaper> references = cachedPaperRepository.findByCachedPaperIdResearch(research);
        references.removeIf(cachedPaper -> cachedPaper.getType() != CachedPaperType.REFERENCE || !papers.contains(cachedPaper.getCachedPaperId().getParentPaper()));
        return references;
    }

    /**
     * Finds all citations from a list of papers that belong to a research
     *
     * @param research the research the citations belong to
     * @param papers   the list of papers whose citations are to be found
     * @return the citations
     */
    public List<CachedPaper> getCachedCitations(Research research, List<Paper> papers) {
        List<CachedPaper> citations = cachedPaperRepository.findByCachedPaperIdResearch(research);
        citations.removeIf(cachedPaper -> cachedPaper.getType() != CachedPaperType.CITATION || !papers.contains(cachedPaper.getCachedPaperId().getParentPaper()));
        return citations;
    }
}

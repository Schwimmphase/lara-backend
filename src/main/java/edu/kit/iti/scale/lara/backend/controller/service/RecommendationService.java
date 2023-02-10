package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.PaperRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ApiActionController apiActionController;
    private final PaperRepository paperRepository;
    private final CacheService cacheService;

    /**
     * Gets a list of recommended papers based on a selection of positive papers and one of negative papers
     *
     * @param positives a list that holds papers where we want to find similar papers
     * @param negatives a list that holds papers where we don't want similar papers
     * @return a list of recommended papers
     * @throws IOException when there was an error getting the recommended papers
     */
    public List<Paper> getRecommendations(List<Paper> positives, List<Paper> negatives) throws IOException {
        if (positives.isEmpty()) {
            return List.of();
        }
        return apiActionController.getRecommendations(positives, negatives);
    }

    /**
     * Finds all references from the list of papers. To optimize runtime the papers are already saved as
     * CachedPapers and only need to be found in the CachedPaperRepository
     *
     * @param research the research references are to be found for
     * @param papers   the papers whose references are to be found
     * @return a list of Cached Papers that hold the referenced papers
     */
    public List<CachedPaper> getCachedReferences(Research research, List<Paper> papers) {
        return cacheService.getCachedReferences(research, papers);
    }

    /**
     * Finds all citations from the list of papers. To optimize runtime the papers are already saved as
     * CachedPapers and only need to be found in the CachedPaperRepository
     *
     * @param research the research citations are to be found for
     * @param papers   the papers whose citations are to be found
     * @return a list of Cached Papers that hold the citing papers
     */
    public List<CachedPaper> getCachedCitations(Research research, List<Paper> papers) {
        return cacheService.getCachedCitations(research, papers);
    }

    /**
     * Is called when a new paper is added to a research.
     * If existing it deletes the cachedPaper that belongs to the research and points to the added paper.
     * It saves all the references and citations for the added paper as CachedPapers into the CachedPaperRepository
     *
     * @param research the research the paper was added to
     * @param paper    the paper that was added
     * @throws IOException when an error occurs getting the references and citations
     */
    public void paperAdded(Research research, Paper paper) throws IOException {
        try {
            cacheService.deleteCachedPaper(paper, research);
        } catch (NotInDataBaseException ignored) {
        }

        List<Paper> citations = apiActionController.getCitations(paper);
        List<Paper> references = apiActionController.getReferences(paper);

        for (Paper citation : citations) {
            paperRepository.save(citation);
            cacheService.createCachedPaper(research, citation, paper, CachedPaperType.CITATION);
        }
        for (Paper reference : references) {
            paperRepository.save(reference);
            cacheService.createCachedPaper(research, reference, paper, CachedPaperType.REFERENCE);
        }
    }

    /**
     * Is called when a paper was removed from the added Papers of a research. Tells the CacheService to delete all
     * to this paper related CachedPapers from the CachedPaperRepository
     *
     * @param research the research the paper was removed from
     * @param paper    the paper that was removed
     */
    public void paperRemoved(Research research, Paper paper) {
        cacheService.removeRelatedCachedPapers(paper, research);
    }
}

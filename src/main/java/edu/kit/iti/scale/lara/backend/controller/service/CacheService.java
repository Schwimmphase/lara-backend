package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.CachedPaperRepository;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;

import java.util.List;

public class CacheService {

    private final CachedPaperRepository cachedPaperRepository;
    private final PaperService paperService;
    private final ApiActionController apiActionController;

    public CacheService(CachedPaperRepository cachedPaperRepository, PaperService paperService, ApiActionController apiActionController) {
        this.cachedPaperRepository = cachedPaperRepository;
        this.paperService = paperService;
        this.apiActionController = apiActionController;
    }

    public void initializeCache(Research research, User user) throws WrongUserException {
        List<SavedPaper> savedPapers = paperService.getSavedPapers(research, user);
        for (SavedPaper savedPaper : savedPapers) {
            List<Paper> citations = apiActionController.getCitations(savedPaper.getPaper());
            List<Paper> references = apiActionController.getReferences(savedPaper.getPaper());

            for (Paper paper : citations) {
                createCachedPaper(research, paper, savedPaper.getPaper(), CachedPaperType.CITATION);
            }
            for (Paper paper : references) {
                createCachedPaper(research, paper, savedPaper.getPaper(), CachedPaperType.REFERENCE);
            }
        }
    }

    public void flushCacheResearch(Research research) {
        List<CachedPaper> cache = cachedPaperRepository.findByResearch(research);
        cachedPaperRepository.deleteAllInBatch(cache);
    }

    public void createCachedPaper(Research research, Paper paper, Paper parent, CachedPaperType cachedPaperType) {
        CachedPaper cachedPaper = new CachedPaper(paper, parent, research, cachedPaperType);
        research.addCachedPaper(cachedPaper);
        cachedPaperRepository.save(cachedPaper);
    }

    public void removePaper(Paper paper, Research research) {
        List<CachedPaper> cachedPapers = cachedPaperRepository.findByParentPaper(paper);
        for (CachedPaper cachedPaper : cachedPapers) {
            if (cachedPaper.getResearch().equals(research)) {
                cachedPaperRepository.delete(cachedPaper);
            }
        }
    }

    public List<CachedPaper> getReferences(Research research, List<Paper> papers) {
        List<CachedPaper> references = cachedPaperRepository.findByResearch(research);
        references.removeIf(cachedPaper -> cachedPaper.getType() != CachedPaperType.REFERENCE || !papers.contains(cachedPaper.getParentPaper()));
        return references;
    }

    public List<CachedPaper> getCitations(Research research, List<Paper> papers) {
        List<CachedPaper> citations = cachedPaperRepository.findByResearch(research);
        citations.removeIf(cachedPaper -> cachedPaper.getType() != CachedPaperType.CITATION || !papers.contains(cachedPaper.getParentPaper()));
        return citations;
    }
}

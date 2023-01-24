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

import java.util.ArrayList;
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

    public List<Paper> getReferences(Research research, List<Paper> papers) { //Todo maybe findByResearch
        List<Paper> references = new ArrayList<>();
        for (Paper parentPaper : papers) {
            for (CachedPaper cachedPaper : cachedPaperRepository.findByParentPaper(parentPaper)) {
                if (cachedPaper.getType() == CachedPaperType.REFERENCE) {
                    references.add(cachedPaper.getPaper());
                }
            }
        }
        return references;
    }

    public List<Paper> getCitations(Research research, List<Paper> papers) { //Todo maybe findByResearch
        List<Paper> citations = new ArrayList<>();
        for (Paper parentPaper : papers) {
            for (CachedPaper cachedPaper : cachedPaperRepository.findByParentPaper(parentPaper)) {
                if (cachedPaper.getType() == CachedPaperType.CITATION) {
                    citations.add(cachedPaper.getPaper());
                }
            }
        }
        return citations;
    }
}

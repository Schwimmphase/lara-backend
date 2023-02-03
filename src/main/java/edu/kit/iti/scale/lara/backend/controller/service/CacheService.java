package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.CachedPaperRepository;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CachedPaperRepository cachedPaperRepository;
    private final ApiActionController apiActionController;

    public void initializeCache(List<SavedPaper> savedPapers, Research research) {
        for (SavedPaper savedPaper : savedPapers) {
            List<Paper> citations = apiActionController.getCitations(savedPaper.getSavedPaperId().getPaper());
            List<Paper> references = apiActionController.getReferences(savedPaper.getSavedPaperId().getPaper());

            for (Paper paper : citations) {
                createCachedPaper(research, paper, savedPaper.getSavedPaperId().getPaper(), CachedPaperType.CITATION);
            }
            for (Paper paper : references) {
                createCachedPaper(research, paper, savedPaper.getSavedPaperId().getPaper(), CachedPaperType.REFERENCE);
            }
        }
    }

    public void flushCacheResearch(Research research) {
        List<CachedPaper> cache = cachedPaperRepository.findByCachedPaperIdResearch(research);
        cachedPaperRepository.deleteAllInBatch(cache);
    }

    public void createCachedPaper(Research research, Paper paper, Paper parent, CachedPaperType cachedPaperType) {
        CachedPaper cachedPaper = new CachedPaper(paper, parent, research, cachedPaperType);
        research.addCachedPaper(cachedPaper);
        cachedPaperRepository.save(cachedPaper);
    }

    public void removePaper(Paper paper, Research research) {
        List<CachedPaper> cachedPapers = cachedPaperRepository.findByCachedPaperIdParentPaper(paper);
        for (CachedPaper cachedPaper : cachedPapers) {
            if (cachedPaper.getCachedPaperId().getResearch().equals(research)) {
                cachedPaperRepository.delete(cachedPaper);
            }
        }
    }

    public List<CachedPaper> getReferences(Research research, List<Paper> papers) {
        List<CachedPaper> references = cachedPaperRepository.findByCachedPaperIdResearch(research);
        references.removeIf(cachedPaper -> cachedPaper.getType() != CachedPaperType.REFERENCE || !papers.contains(cachedPaper.getCachedPaperId().getParentPaper()));
        return references;
    }

    public List<CachedPaper> getCitations(Research research, List<Paper> papers) {
        List<CachedPaper> citations = cachedPaperRepository.findByCachedPaperIdResearch(research);
        citations.removeIf(cachedPaper -> cachedPaper.getType() != CachedPaperType.CITATION || !papers.contains(cachedPaper.getCachedPaperId().getParentPaper()));
        return citations;
    }
}

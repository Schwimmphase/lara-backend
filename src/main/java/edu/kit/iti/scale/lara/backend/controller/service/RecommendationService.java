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


    public List<Paper> getRecommendations(List<Paper> positives, List<Paper> negatives) throws IOException {
        if (positives.isEmpty()) {
            return List.of();
        }

        return apiActionController.getRecommendations(positives, negatives);
    }

    public List<CachedPaper> getCachedReferences(Research research, List<Paper> papers) {
        return cacheService.getCachedReferences(research, papers);
    }

    public List<CachedPaper> getCachedCitations(Research research, List<Paper> papers) {
        return cacheService.getCachedCitations(research, papers);
    }

    public void paperAdded(Research research, Paper paper) throws IOException {
        try {
            cacheService.deleteCachedPaper(paper, research);
        } catch (NotInDataBaseException ignored) {//should never happen
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

    public void paperRemoved(Research research, Paper paper) {
        cacheService.removeRelatedCachedPapers(paper, research);
    }
}

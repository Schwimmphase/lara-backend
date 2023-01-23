package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

public class RecommendationService {

    private final ApiActionController apiActionController;
    private final CacheService cacheService;

    public RecommendationService(ApiActionController apiActionController, CacheService cacheService) {
        this.apiActionController = apiActionController;
        this.cacheService = cacheService;
    }


    public List<Paper> getRecommendationsOfPapers(List<Paper> positives, List<Paper> negatives) {
        return apiActionController.getRecommendations(positives, negatives);
    }

    public List<Paper> getReferencesOfPapers(List<Paper> papers) {
        return cacheService.getReferences(null, papers); //Todo research
    }

    public List<Paper> getCitationsOfPapers(List<Paper> papers) {
        return cacheService.getCitations(null, papers); //todo research
    }

    public void paperAdded(Research research, Paper paper) {

        // TODO

    }

    public void paperRemoved(Research research, Paper paper) {

        // TODO

    }
}

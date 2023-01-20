package edu.kit.iti.scale.lara.backend.controller.api.apihandler;

import java.util.List;

public interface ApiHandler {

    List<ApiPaper> getPapersByKeyword(String query);

    List<ApiPaper> getRecommendations(List<String> positiveIds, List<String> negativeIds);

    List<ApiPaper> getCitations(String paperId);

    List<ApiPaper> getReferences(String paperId);

    ApiPaper getPaper(String paperId);

}

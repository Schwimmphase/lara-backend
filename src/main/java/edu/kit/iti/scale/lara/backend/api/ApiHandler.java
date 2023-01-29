package edu.kit.iti.scale.lara.backend.api;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface ApiHandler {

    List<ApiPaper> getPapersByKeyword(String query) throws IOException, JSONException;

    List<ApiPaper> getRecommendations(List<String> positiveIds, List<String> negativeIds) throws IOException, JSONException;

    List<ApiPaper> getCitations(String paperId) throws IOException, JSONException;

    List<ApiPaper> getReferences(String paperId) throws IOException, JSONException;

    ApiPaper getPaper(String paperId) throws JSONException, IOException;

}

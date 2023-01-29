package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiHandler;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import edu.kit.iti.scale.lara.backend.api.IdParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SemanticScholarHandler implements ApiHandler {

    private static final String URL = "https://api.semanticscholar.org/graph/v1/paper/";
    private static final String RECOMMENDATION_URL = "https://api.semanticscholar.org/recommendations/v1/papers/";
    private static final String SEARCH = "search?query=";
    private static final String CITATIONS = "/citations";
    private static final String REFERENCES = "/references";
    private static final String PAPER_ATTRIBUTES ="?fields=title,authors,venue,year,citationCount,referenceCount,abstract,url";
    private static final HttpMethod GET = HttpMethod.GET;
    private static final HttpMethod POST = HttpMethod.POST;

    @Override
    public List<ApiPaper> getPapersByKeyword(String query) throws IOException, JSONException {

        // call Api
        String response = new SemanticScholarCaller().call(URL + SEARCH + query.replace(" ", "+") + "&" + PAPER_ATTRIBUTES, GET, null, null);

        // convert response String to api papers
        List<ApiPaper> papersByKeyword = new SemanticScholarWrapper().convertCitationsReferencesSearch(response);

        return papersByKeyword;
    }

    @Override
    public List<ApiPaper> getRecommendations(List<String> positiveIds, List<String> negativeIds) throws IOException, JSONException {

        // converting lara Ids to semantic scholar Ids
        positiveIds = parseIds(positiveIds);
        negativeIds = parseIds(negativeIds);

        JSONArray positives = convertToJsonArray(positiveIds);
        JSONArray negatives = convertToJsonArray(negativeIds);

        // call Api
        String response = new SemanticScholarCaller().call(RECOMMENDATION_URL + PAPER_ATTRIBUTES, POST, positives, negatives);

        //convert response String to api papers
        List<ApiPaper> recommendations = new SemanticScholarWrapper().convertRecommendations(response);
        return null;
    }

    @Override
    public List<ApiPaper> getCitations(String paperId) throws IOException, JSONException {

        // converting lara Id to semantic scholar Id
        paperId = parseIds(List.of(paperId)).get(0);

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + CITATIONS + PAPER_ATTRIBUTES, GET, null, null);

        // convert response String to api papers
        List<ApiPaper> citations = new SemanticScholarWrapper().convertCitationsReferencesSearch(response);

        return citations;
    }

    @Override
    public List<ApiPaper> getReferences(String paperId) throws IOException, JSONException {

        // converting lara Id to semantic scholar Id
        paperId = parseIds(List.of(paperId)).get(0);

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + REFERENCES + PAPER_ATTRIBUTES, GET, null, null);

        // convert response String to api papers
        List<ApiPaper> references = new SemanticScholarWrapper().convertCitationsReferencesSearch(response);

        return references;
    }

    @Override
    public ApiPaper getPaper(String paperId) throws JSONException, IOException {

        // converting lara Id to semantic scholar Id
        paperId = parseIds(List.of(paperId)).get(0);

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + PAPER_ATTRIBUTES, GET, null, null);

        // convert response String to api paper
        List<ApiPaper> apiPaper = new SemanticScholarWrapper().convertToPaper(response);

        return apiPaper.get(0);
    }


    private JSONArray convertToJsonArray(List<String> ids) {

        JSONArray jsonIds = new JSONArray();

        for (String id : ids) {
            jsonIds.put(id);
        }

        return jsonIds;
    }

    private List<String> parseIds(List<String> laraIds) {

        List<String> semanticScholarIds = new ArrayList<>();

        for (String laraId : laraIds) {
            semanticScholarIds.add(new IdParser().decodedId(laraId));
        }

        return semanticScholarIds;
    }
}

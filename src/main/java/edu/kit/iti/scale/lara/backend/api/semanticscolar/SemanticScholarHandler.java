package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiHandler;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import edu.kit.iti.scale.lara.backend.api.IdParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a handler for the Semantic Scholar API.
 *
 * @author uefjv
 */
public class SemanticScholarHandler implements ApiHandler {

    private static final String URL = "https://api.semanticscholar.org/graph/v1/paper/";
    private static final String RECOMMENDATION_URL = "https://api.semanticscholar.org/recommendations/v1/papers/?";
    private static final String SEARCH = "search?limit=100&query=";
    private static final String CITATIONS = "/citations";
    private static final String REFERENCES = "/references";
    private static final String PAPER_ATTRIBUTES ="fields=title,authors,venue,year,citationCount,referenceCount,abstract,openAccessPdf,externalIds";
    private static final String CITED_PAPER = "citedPaper";
    private static final String CITING_PAPER = "citingPaper";
    private static final String RECOMMENDED_PAPERS = "recommendedPapers";
    private static final String DATA = "data";
    private static final String POSITIVE_PAPER_IDS = "positivePaperIds";
    private static final String NEGATIVE_PAPER_IDS = "negativePaperIds";
    private static final HttpMethod GET = HttpMethod.GET;
    private static final HttpMethod POST = HttpMethod.POST;


    private final SemanticScholarWrapper semanticScholarWrapper = new SemanticScholarWrapper();
    private final SemanticScholarCaller semanticScholarCaller = new SemanticScholarCaller();

    @Override
    public List<ApiPaper> getPapersByKeyword(String query) throws IOException, JSONException {
        // call Api
        String response = semanticScholarCaller.call(URL + SEARCH + query.replace(" ", "+") +
                "&" + PAPER_ATTRIBUTES, GET);

        // convert response String to api papers
        return semanticScholarWrapper.convertRecommendationsSearchResults(response, DATA);
    }

    @Override
    public List<ApiPaper> getRecommendations(List<String> positiveIds, List<String> negativeIds) throws IOException, JSONException {
        // converting lara Ids to semantic scholar Ids
        positiveIds = parseIds(positiveIds);
        negativeIds = parseIds(negativeIds);

        // create body as JSONObject
        JSONArray positives = convertToJsonArray(positiveIds);
        JSONArray negatives = convertToJsonArray(negativeIds);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put(POSITIVE_PAPER_IDS, positives);
        jsonBody.put(NEGATIVE_PAPER_IDS, negatives);

        // call Api
        String response = semanticScholarCaller.call(RECOMMENDATION_URL + PAPER_ATTRIBUTES, POST, jsonBody);

        //convert response String to api papers
        return semanticScholarWrapper.convertRecommendationsSearchResults(response, RECOMMENDED_PAPERS);
    }

    @Override
    public List<ApiPaper> getCitations(String paperId) throws IOException, JSONException {
        // converting lara Id to semantic scholar Id
        paperId = parseIds(List.of(paperId)).get(0);

        // call Api
        String response = semanticScholarCaller.call(URL + paperId + CITATIONS + "?" + PAPER_ATTRIBUTES, GET);


        // convert response String to api papers
        return semanticScholarWrapper.convertCitationsReferencesSearch(response, CITING_PAPER);
    }

    @Override
    public List<ApiPaper> getReferences(String paperId) throws IOException, JSONException {
        // converting lara Id to semantic scholar Id
        paperId = parseIds(List.of(paperId)).get(0);

        // call Api
        String response = semanticScholarCaller.call(URL + paperId + REFERENCES + "?" + PAPER_ATTRIBUTES, GET);

        // convert response String to api papers
        return semanticScholarWrapper.convertCitationsReferencesSearch(response, CITED_PAPER);
    }

    @Override
    public ApiPaper getPaper(String paperId) throws JSONException, IOException {
        // converting lara Id to semantic scholar Id
        paperId = parseIds(List.of(paperId)).get(0);

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + "?" + PAPER_ATTRIBUTES, GET);

        // convert response String to api paper
        List<ApiPaper> apiPaper = new SemanticScholarWrapper().convertToPaper(response);

        if (apiPaper.isEmpty()) {
            throw new IllegalArgumentException("No paper found for id: " + paperId);
        }

        return apiPaper.get(0);
    }


    private JSONArray convertToJsonArray(List<String> ids) {
        JSONArray jsonIds = new JSONArray();

        ids.forEach(jsonIds::put);

        return jsonIds;
    }

    private List<String> parseIds(List<String> laraIds) {
        return laraIds.stream().map(laraId -> new IdParser().decodedId(laraId)).collect(Collectors.toList());
    }
}

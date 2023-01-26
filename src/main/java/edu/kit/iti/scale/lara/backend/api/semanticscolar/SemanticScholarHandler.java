package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiHandler;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class SemanticScholarHandler implements ApiHandler {

    private static final String URL = "https://api.semanticscholar.org/graph/v1/paper/";
    private static final String RECOMMENDATION_URL = "https://api.semanticscholar.org/recommendations/v1/";
    private static final String SEARCH = "search?query=";
    private static final String CITATIONS = "/citations?fields=title,authors,venue,year,abstract,citationCount,referenceCount,url";
    private static final String REFERENCES = "/references?fields=title,authors,venue,year,abstract,citationCount,referenceCount,url";
    private static final String PAPER_ATTRIBUTES = "?fields=title,authors,venue,year,abstract,citationCount,referenceCount,url";
    private static final HttpMethod GET = HttpMethod.GET;
    private static final HttpMethod POST = HttpMethod.POST;

    @Override
    public List<ApiPaper> getPapersByKeyword(String query) throws IOException, JSONException {

        // format query for api request
        query = query.replace(" ", "+");

        // call Api
        String response = new SemanticScholarCaller().call(URL + SEARCH + query + PAPER_ATTRIBUTES, GET);

        // convert response String to api papers
        List<ApiPaper> papersByKeyword = new SemanticScholarWrapper().convertCitationsReferencesSearch(response);

        return papersByKeyword;
    }

    @Override
    public List<ApiPaper> getRecommendations(List<String> positiveIds, List<String> negativeIds) {

        // call Api
        //String response = new SemanticScholarCaller().call();


        // TODO: how to send POST request with body ??

        return null;
    }

    @Override
    public List<ApiPaper> getCitations(String paperId) throws IOException, JSONException {

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + CITATIONS, GET);

        // convert response String to api papers
        List<ApiPaper> citations = new SemanticScholarWrapper().convertCitationsReferencesSearch(response);

        return citations;
    }

    @Override
    public List<ApiPaper> getReferences(String paperId) throws IOException, JSONException {

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + REFERENCES, GET);

        // convert response String to api papers
        List<ApiPaper> references = new SemanticScholarWrapper().convertCitationsReferencesSearch(response);

        return references;
    }

    @Override
    public ApiPaper getPaper(String paperId) throws JSONException, IOException {

        // call Api
        String response = new SemanticScholarCaller().call(URL + paperId + PAPER_ATTRIBUTES, GET);

        // convert response String to api paper
        List<ApiPaper> apiPaper = new SemanticScholarWrapper().convertToPaper(response);

        return apiPaper.get(0);
    }
}

package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.nimbusds.jose.JOSEException;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SemanticScholarCallerTest {

    public static final String RECOMMENDED_PAPERS = "recommendedPapers";
    public static final String DATA = "data";
    public static final String CITED_PAPER = "citedPaper";
    public static final String CITING_PAPER = "citingPaper";
    public static final String PAPER_ID = "paperId";
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String VENUE = "venue";
    public static final String AUTHORS = "authors";


    @Test
    void callPostRequestRecommendations() throws IOException {

        // set up
        JSONArray positives = new JSONArray().put("385742fffcf113656f0d3cf6c06ef95cb8439dc6");
        JSONArray negatives = new JSONArray().put("e24cdf73b3e7e590c2fe5ecac9ae8aa983801367");
        JSONObject jsonBody = new JSONObject("{\"positivePaperIds\": " + positives.toString() + ",\n \"negativePaperIds\": " + negatives.toString() + "}");
        HttpMethod method = HttpMethod.POST;
        String url = "https://api.semanticscholar.org/recommendations/v1/papers/?offset=100&limit=10&fields=url,abstract,authors,title,venue,year,citationCount,referenceCount";


        // execution
        String response = new SemanticScholarCaller().call(url, method, jsonBody);

        JSONObject jsonResponse = new JSONObject(response);

        //System.out.println(response);

        // test
        assertNotNull(response);
        assertNotNull(jsonResponse.getJSONArray(RECOMMENDED_PAPERS));
        assertEquals(10, jsonResponse.getJSONArray(RECOMMENDED_PAPERS).length());

        // testing for all attributes of result papers
        JSONArray responsePapers = jsonResponse.getJSONArray(RECOMMENDED_PAPERS);
        for (int i = 0; i < responsePapers.length(); i++) {

            assertNotNull(new JSONObject(responsePapers.get(i).toString()).getString(PAPER_ID));
            assertNotNull(new JSONObject(responsePapers.get(i).toString()).getString(URL));
            assertNotNull(new JSONObject(responsePapers.get(i).toString()).getString(TITLE));
            // abstract can be null.
            assertNotNull(new JSONObject(responsePapers.get(i).toString()).getString(VENUE));
            assertNotNull(new JSONObject(responsePapers.get(i).toString()).getJSONArray(AUTHORS));
        }
    }

    @Test
    void callGetRequestSearch() throws IOException {

        // set up Search request
        String url = "https://api.semanticscholar.org/graph/v1/paper/search?query=literature+graph&fields=authors,title,venue,year,citationCount,referenceCount,abstract,url";
        HttpMethod method = HttpMethod.GET;

        // execution
        String response = new SemanticScholarCaller().call(url, method, null);

        JSONObject jsonResponse = new JSONObject(response);

        //System.out.println(response);

        // test
        assertNotNull(response);
        assertNotNull(jsonResponse.getJSONArray(DATA));
        assertEquals(10, jsonResponse.getJSONArray(DATA).length());

        // testing for all attributes of result papers
        JSONArray responsePapers = jsonResponse.getJSONArray(DATA);
        for (int i = 0; i < responsePapers.length(); i++) {

            assertNotNull(((JSONObject) responsePapers.get(i)).getString(PAPER_ID));
            assertNotNull(((JSONObject) responsePapers.get(i)).getString(URL));
            assertNotNull(((JSONObject) responsePapers.get(i)).getString(TITLE));
            // abstract can be null.
            assertNotNull(((JSONObject) responsePapers.get(i)).getString(VENUE));
            assertNotNull(((JSONObject) responsePapers.get(i)).getJSONArray(AUTHORS));
        }
    }

    @Test
    void callGetRequestCitations() throws IOException {

        // set up Search request
        String url = "https://api.semanticscholar.org/graph/v1/paper/cb13b1b6a37e4080d8c13c5f33694b5aae90abcf/citations?fields=title,authors,venue,year,citationCount,referenceCount,abstract,url";
        HttpMethod method = HttpMethod.GET;

        // execution
        String response = new SemanticScholarCaller().call(url, method, null);

        JSONObject jsonResponse = new JSONObject(response);

        //System.out.println(response);

        // test
        assertNotNull(response);
        assertNotNull(jsonResponse.getJSONArray(DATA));
        assertEquals(20, jsonResponse.getJSONArray(DATA).length());

        // testing for all attributes of result papers
        JSONArray responsePapers = jsonResponse.getJSONArray(DATA);
        for (int i = 0; i < responsePapers.length(); i++) {


            JSONObject paper = ((JSONObject) responsePapers.get(i)).getJSONObject(CITING_PAPER);

            assertNotNull(paper.getString(PAPER_ID));
            assertNotNull(paper.getString(URL));
            assertNotNull(paper.getString(TITLE));
            // abstract can be null.
            assertNotNull(paper.getString(VENUE));
            assertNotNull(paper.getJSONArray(AUTHORS));
        }
    }

    @Test
    void callGetRequestReferences() throws IOException {

        // set up Search request
        String url = "https://api.semanticscholar.org/graph/v1/paper/649def34f8be52c8b66281af98ae884c09aef38b/references?fields=title,authors,venue,year,citationCount,referenceCount,abstract,url";
        HttpMethod method = HttpMethod.GET;

        // execution
        String response = new SemanticScholarCaller().call(url, method, null);

        JSONObject jsonResponse = new JSONObject(response);

        //System.out.println(response);

        // test
        assertNotNull(response);
        assertNotNull(jsonResponse.getJSONArray(DATA));

        // testing for all attributes of result papers
        JSONArray responsePapers = jsonResponse.getJSONArray(DATA);
        for (int i = 0; i < responsePapers.length(); i++) {


            JSONObject paper = ((JSONObject) responsePapers.get(i)).getJSONObject(CITED_PAPER);

            assertNotNull(paper.getString(PAPER_ID));
            assertNotNull(paper.getString(URL));
            assertNotNull(paper.getString(TITLE));
            // abstract can be null.
            assertNotNull(paper.getString(VENUE));
            assertNotNull(paper.getJSONArray(AUTHORS));
        }
    }

    @Test
    void callGetPaper() throws IOException{

        // set up
        String url = "https://api.semanticscholar.org/graph/v1/paper/649def34f8be52c8b66281af98ae884c09aef38b?fields=title,authors,venue,year,citationCount,referenceCount,abstract,url";
        HttpMethod method = HttpMethod.GET;

        // execution
        String response = new SemanticScholarCaller().call(url, method, null);

        JSONObject jsonResponse = new JSONObject(response);

        //System.out.println(response);

        // test
        assertNotNull(response);

        // testing for all attributes of result paper

        assertNotNull(jsonResponse.getString(PAPER_ID));
        assertNotNull(jsonResponse.getString(URL));
        assertNotNull(jsonResponse.getString(TITLE));
        // abstract can be null.
        assertNotNull(jsonResponse.getString(VENUE));
        assertNotNull(jsonResponse.getJSONArray(AUTHORS));
    }
}
package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.ApiWrapper;
import edu.kit.iti.scale.lara.backend.api.IdParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SemanticScholarWrapper implements ApiWrapper {

    private static final String API_PREFIX = "SemSchol";
    private static final String DATA = "data";

    private IdParser idParser = new IdParser();


    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ApiPaper> convertToPaper(String response) throws JsonProcessingException {

        SemanticScholarPaper paper = objectMapper.readValue(response, SemanticScholarPaper.class);

        // changing ID from SemanticScholar id to our lara ID: SemSchol${SemanticScholarID}

        ApiPaper apiPaper = new ApiPaper(paper.authors(), idParser.encodedId(API_PREFIX, paper.id()), paper.title(),
                paper.year(), paper.abstractText(), paper.citationCount(), paper.referenceCount(), paper.venue(),
                paper.openAccessPdf() == null ? getArXivPdf(paper) : getPdf(paper));


        // TODO: skipping papers with Id null
        if (apiPaper.id() == null) {
            return new ArrayList<>();
        } else {
            return List.of(apiPaper);
        }
    }


    public List<ApiPaper> convertRecommendationsSearchResults(String response, String type) throws JsonProcessingException, JSONException {

        List<ApiPaper> apiPapers = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(response);

        JSONArray jsonPapers = jsonResponse.getJSONArray(type);

        for (Object jsonPaper : jsonPapers) {
            apiPapers.addAll(convertToPaper(jsonPaper.toString()));
        }

        return apiPapers;
    }

    public List<ApiPaper> convertCitationsReferencesSearch(String response, String type) throws JSONException, JsonProcessingException {

        List<ApiPaper> apiPapers = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(response);

        JSONArray jsonPapers = jsonResponse.getJSONArray(DATA);

        for (Object jsonPaper : jsonPapers) {
            apiPapers.addAll(convertToPaper(((JSONObject) jsonPaper).getJSONObject(type).toString()));
        }

        return apiPapers;
    }

    private String getArXivPdf(SemanticScholarPaper paper) {
        if (paper.externalIds() == null) {
            return null;
        }
        if (paper.externalIds().containsKey("ArXiv")) {
            return "https://arxiv.org/pdf/" + paper.externalIds().get("ArXiv") + ".pdf";
        }
        return null;
    }

    private String getPdf(SemanticScholarPaper paper) {
        return paper.openAccessPdf().url().replace("http:", "https:"); //replace http: in url with https: so there are no problems displaying the pdf
    }
}

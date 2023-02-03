package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.iti.scale.lara.backend.api.IdParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.ApiWrapper;
import java.util.ArrayList;
import java.util.List;

public class SemanticScholarWrapper implements ApiWrapper {

    private static final String API_PREFIX = "SemSchol";
    private static final String DATA = "data";

    private IdParser idParser = new IdParser();


    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ApiPaper> convertToPaper(String response) throws JsonProcessingException {

        ApiPaper apiPaper = objectMapper.readValue(response, ApiPaper.class);

        // changing ID from SemanticScholar id to our lara ID: SemSchol#{SemanticScholarID}
        apiPaper.setId(idParser.encodedId(API_PREFIX, apiPaper.getId()));

        // TODO: skipping papers with Id null
        if (apiPaper.getId() == null) {
            return new ArrayList<>();
        }
        else {
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
}

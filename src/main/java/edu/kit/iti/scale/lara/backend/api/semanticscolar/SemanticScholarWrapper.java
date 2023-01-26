package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.ApiWrapper;
import java.util.ArrayList;
import java.util.List;

public class SemanticScholarWrapper implements ApiWrapper {

    private static final String DATA = "data";
    private static final String CITED_PAPER = "citedPaper";
    private static final String RECOMMENDED_PAPERS = "recommendedPapers";

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<ApiPaper> convertToPaper(String response) throws JsonProcessingException {

        ApiPaper apiPaper = objectMapper.readValue(response, ApiPaper.class);

        return List.of(apiPaper);
    }


    public List<ApiPaper> convertRecommendations(String response) throws JsonProcessingException, JSONException {

        List<ApiPaper> apiPapers = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(response);

        JSONArray jsonPapers = jsonResponse.getJSONArray(RECOMMENDED_PAPERS);

        for (int i = 0; i < jsonPapers.length() - 1; i++) {
            JSONObject jsonPaper = (JSONObject) jsonPapers.get(i);
            String paperString = jsonPaper.toString();
            apiPapers.addAll(convertToPaper(paperString));
        }

        return apiPapers;
    }

    public List<ApiPaper> convertCitationsReferencesSearch(String response) throws JSONException, JsonProcessingException {

        List<ApiPaper> apiPapers = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(response);

        JSONArray jsonPapers = jsonResponse.getJSONArray(DATA);

        for (int i = 0; i < jsonPapers.length(); i++) {
            JSONObject jsonPaper = (JSONObject) jsonPapers.get(i);
            String paperString = jsonPaper.getString(CITED_PAPER);
            apiPapers.addAll(convertToPaper(paperString));
        }

        return apiPapers;
    }
}

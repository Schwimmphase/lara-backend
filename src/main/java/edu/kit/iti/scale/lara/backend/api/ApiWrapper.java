package edu.kit.iti.scale.lara.backend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import org.json.JSONException;

import java.util.List;

public interface ApiWrapper {

    List<ApiPaper> convertToPaper(String response) throws JSONException, JsonProcessingException;

}

package edu.kit.iti.scale.lara.backend.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;

import java.util.List;

/**
 * Interface for the API wrapper
 *
 * @author uefjv
 */
public interface ApiWrapper {

    List<ApiPaper> convertToPaper(String response) throws JSONException, JsonProcessingException;

}

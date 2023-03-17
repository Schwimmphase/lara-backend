package edu.kit.iti.scale.lara.backend.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Interface for the API caller
 *
 * @author uefjv
 */
public interface ApiCaller {

    String call(String url, HttpMethod method, JSONObject jsonBody) throws IOException, JSONException;

}

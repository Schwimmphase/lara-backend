package edu.kit.iti.scale.lara.backend.api;

import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ApiCaller {

    String call(String url, HttpMethod method, JSONObject jsonBody) throws IOException, JSONException;

}

package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiCaller;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class SemanticScholarCaller implements ApiCaller {


    @Override
    public String call(String url, HttpMethod method, JSONObject jsonBody) throws IOException {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request;

        if (method == HttpMethod.POST) {
            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
            request = new Request.Builder().url(new URL(url)).post(body).build();
        }
        else {
            request = new Request.Builder().url(new URL(url)).build();
        }

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();

        return response.body().string();
    }

    public String call(String url, HttpMethod method) throws IOException {
        return call(url, method, null);
    }
}

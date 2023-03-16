package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiCaller;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;
import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class SemanticScholarCaller implements ApiCaller {

    private static final int HTTP_STATE_OK = 200;

    private final OkHttpClient client;

    public SemanticScholarCaller() {
        client = new OkHttpClient().newBuilder()
                .callTimeout(Duration.of(60, ChronoUnit.SECONDS))
                .build();
    }

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

        Call call = client.newCall(request);
        Response response = call.execute();

        // checking if request was valid
        if (response.code() != HTTP_STATE_OK) {
            throw new RuntimeException("Request failed with code " + response.code() + " and message " + response.message());
        }

        return Objects.requireNonNull(response.body()).string();
    }

    public String call(String url, HttpMethod method) throws IOException {
        return call(url, method, null);
    }
}

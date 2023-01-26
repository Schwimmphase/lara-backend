package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiCaller;
import edu.kit.iti.scale.lara.backend.api.HttpMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SemanticScholarCaller implements ApiCaller {


    @Override
    public String call(String url, HttpMethod method) throws IOException {

        URL url1 = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

        return null;
    }
}

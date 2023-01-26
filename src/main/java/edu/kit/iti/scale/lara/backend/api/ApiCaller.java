package edu.kit.iti.scale.lara.backend.api;

import edu.kit.iti.scale.lara.backend.api.HttpMethod;

import java.io.IOException;
import java.net.MalformedURLException;

public interface ApiCaller {

    String call(String url, HttpMethod method) throws IOException;

}

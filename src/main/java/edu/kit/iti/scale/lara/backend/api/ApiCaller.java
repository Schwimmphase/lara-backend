package edu.kit.iti.scale.lara.backend.api;

import edu.kit.iti.scale.lara.backend.api.HttpMethod;

public interface ApiCaller {

    String call(String url, HttpMethod method);

}

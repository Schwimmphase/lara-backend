package edu.kit.iti.scale.lara.backend.api;

import edu.kit.iti.scale.lara.backend.api.ApiPaper;

import java.util.List;

public interface ApiWrapper {

    List<ApiPaper> convertToPaper(String response);

}

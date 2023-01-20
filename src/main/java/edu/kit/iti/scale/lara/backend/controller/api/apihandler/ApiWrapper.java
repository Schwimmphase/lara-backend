package edu.kit.iti.scale.lara.backend.controller.api.apihandler;

import java.util.List;

public interface ApiWrapper {

    List<ApiPaper> convertToPaper(String response);

}

package edu.kit.iti.scale.lara.backend.controller.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ApiActionController {

    public Paper getPaper(String paperId) {

        // TODO

        return null;
    }

    public List<Paper> getPapersByKeyword(String query) {

        // TODO

        return null;
    }

    public List<Paper> getRecommendations(List<Paper> positives, List<Paper> negatives) {

        // TODO

        return null;
    }

    public List<Paper> getCitations(Paper paper) {

        // TODO

        return null;
    }

    public List<Paper> getReferences(Paper paper) {

        // TODO

        return null;
    }
}

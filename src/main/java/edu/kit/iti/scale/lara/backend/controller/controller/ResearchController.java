package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.request.ResearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ResearchController {

    public ResponseEntity<Research> createResearch(ResearchRequest request, User user) {

        // TODO

        return null;
    }

    public List<Research> listResearch(User user) {

        // TODO

        return null;
    }

    public ResponseEntity<Research> updateResearch(String researchId, ResearchRequest request, User user){

        // TODO

        return null;
    }

    public HttpStatus deleteResearch(String researchId, User user) {

        // TODO

        return null;
    }

    public HttpStatus savePaper(String researchId, String paperId, SaveState saveState, User user) {

        // TODO

        return null;
    }

    public HttpStatus deletePaper(String researchId, String paperId, User user) {

        // TODO

        return null;
    }

    public ResponseEntity<List<Tag>> researchTags(String researchId, User user) {

        // TODO

        return null;
    }

    public ResponseEntity<List<Paper>> researchPapers(String researchId, List<OrganizerRequest> requestList, User user) {

        // TODO

        return null;
    }

    public ResponseEntity<List<Paper>> researchRecommendations(String researchId, RecommendationMethod method, List<OrganizerRequest> requestList, User user) {

        // TODO

        return null;
    }

    public ResponseEntity<List<Paper>> researchSearch(String query, List<OrganizerRequest> requestList, User user) {

        // TODO

        return null;
    }
}

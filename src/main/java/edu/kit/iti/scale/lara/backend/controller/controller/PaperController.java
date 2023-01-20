package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PaperController {

    public ResponseEntity<Paper> paperDetails(String id, User user) {

        // TODO

        return null;
    }

    public HttpStatus paperAddTag(String id, String tagId, User user) {

        // TODO

        return null;
    }

    public HttpStatus paperTagRemove(String id, String tagId, User user) {

        // TODO

        return null;
    }

    public HttpStatus paperComment(String id, String comment, User user) {

        // TODO

        return null;
    }

    public HttpStatus paperSaveState(String id, SaveState saveState, User user) {

        // TODO

        return null;
    }

    public HttpStatus paperRelevance(String id, int relevance, User user) {

        // TODO

        return null;
    }
}

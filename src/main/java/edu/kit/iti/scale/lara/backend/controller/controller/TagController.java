package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.record.TagRequest;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    public ResponseEntity<Tag> createTag(String name, String researchId, User user) {

        // TODO

        return null;
    }

    public ResponseEntity<Tag> updateTag(String id, TagRequest request, User user) {

        // TODO

        return null;
    }

    public HttpStatus deleteTag(String id, User user) {

        // TODO

        return null;
    }
}

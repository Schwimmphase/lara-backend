package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.TagRequest;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    public ResponseEntity<Tag> createTag(String name, String researchId, User user) {

        //mock
        return ResponseEntity.ok(new Tag("12345", "#0000FF", "New-Tag"));
    }

    public ResponseEntity<Tag> updateTag(String id, TagRequest request, User user) {

        //mock
        return ResponseEntity.ok(new Tag("12345", "#0000FF", "Updated-Tag"));
    }

    public HttpStatus deleteTag(String id, User user) {

        //mock
        return HttpStatus.OK;
    }
}

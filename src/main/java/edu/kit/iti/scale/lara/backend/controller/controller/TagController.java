package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.TagRequest;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class TagController {

    public ResponseEntity<Tag> createTag(String name, String researchId, User user) {

        //mock
        Research research = new Research("12345", "randomResearch", new Comment("12345", "text"), new Date(2023, Calendar.JANUARY, 1));

        return ResponseEntity.ok(new Tag("12345", "#0000FF", "New-Tag", research));
    }

    public ResponseEntity<Tag> updateTag(String id, TagRequest request, User user) {

        //mock
        Research research = new Research("12345", "randomResearch", new Comment("12345", "text"), new Date(2023, Calendar.JANUARY, 1));

        return ResponseEntity.ok(new Tag("12345", "#0000FF", "Updated-Tag", research));
    }

    public HttpStatus deleteTag(String id, User user) {

        //mock
        return HttpStatus.OK;
    }
}

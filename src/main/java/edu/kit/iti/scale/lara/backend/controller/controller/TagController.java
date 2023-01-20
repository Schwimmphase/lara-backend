package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.TagRequest;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class TagController {

    public ResponseEntity<Tag> createTag(String name, String researchId, User user) {

        //mock
        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "randomResearch", new Comment("12345", "text"), ZonedDateTime.now(), user1);

        return ResponseEntity.ok(new Tag("12345", "#0000FF", "New-Tag", research));
    }

    public ResponseEntity<Tag> updateTag(String id, TagRequest request, User user) {

        //mock
        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "randomResearch", new Comment("12345", "text"), ZonedDateTime.now(), user1);

        return ResponseEntity.ok(new Tag("12345", "#0000FF", "Updated-Tag", research));
    }

    public HttpStatus deleteTag(String id, User user) {

        //mock
        return HttpStatus.OK;
    }
}

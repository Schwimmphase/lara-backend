package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.TagRequest;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final ResearchService researchService;

    @PostMapping("/")
    public ResponseEntity<Tag> createTag(@RequestParam String researchId, @RequestBody TagRequest request, User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            Tag tag = tagService.createTag(request.color(), request.name(), research);
            return ResponseEntity.ok(tag);
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable String id, @RequestBody TagRequest request, User user) {
        try {
            Tag tag = tagService.getTag(id, user);
            tagService.updateTag(tag, request.name(), request.color());
            return ResponseEntity.ok(tag);
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable String id, User user) {
        try {
            Tag tag = tagService.getTag(id, user);
            tagService.deleteTag(tag);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }
}
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
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final ResearchService researchService;

    @PostMapping("")
    public ResponseEntity<Tag> createTag(@RequestParam @NotNull String researchId,
                                         @RequestBody @NotNull TagRequest request,
                                         @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            Tag tag = tagService.createTag(request.color(), request.name(), research);
            return ResponseEntity.ok(tag);
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable @NotNull String id,
                                         @RequestBody @NotNull TagRequest request,
                                         @RequestAttribute("user") User user) {
        try {
            Tag tag = tagService.getTag(id, user);
            tagService.updateTag(tag, request.name(), request.color());
            return ResponseEntity.ok(tag);
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable @NotNull String id, @RequestAttribute("user") User user) {
        try {
            Tag tag = tagService.getTag(id, user);
            tagService.deleteTag(tag);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }
}
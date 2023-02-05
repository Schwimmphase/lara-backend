package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.controller.service.RecommendationService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.organizer.OrganizerList;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paper")
@RequiredArgsConstructor
public class PaperController {

    private final ApiActionController apiActionController;
    private final PaperService paperService;
    private final ResearchService researchService;
    private final TagService tagService;
    private final RecommendationService recommendationService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> paperDetails(@PathVariable @NotNull String id,
                                               @RequestParam(required = false) @Nullable String researchId,
                                               @RequestAttribute("user") User user) {
        if (researchId == null) {
            try {
                return ResponseEntity.ok(apiActionController.getPaper(id));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            return ResponseEntity.ok(savedPaper);
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    @PutMapping("/{id}/tag")
    public ResponseEntity<Void> paperAddTag(@PathVariable @NotNull String id,
                                            @RequestParam @NotNull String researchId,
                                            @RequestParam @NotNull String tagId,
                                            @RequestAttribute("user") User user) {

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            Tag tag = tagService.getTag(tagId, user);
            paperService.addTagToPaper(savedPaper, tag);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper or tag not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    @DeleteMapping("/{id}/tag")
    public ResponseEntity<Void> paperTagRemove(@PathVariable @NotNull String id,
                                               @RequestParam @NotNull String researchId,
                                               @RequestParam @NotNull String tagId,
                                               @RequestAttribute("user") User user) {

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            Tag tag = tagService.getTag(tagId, user);
            paperService.removeTagFromPaper(savedPaper, tag);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper or tag not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    @PatchMapping("/{id}/comment")
    public ResponseEntity<Void> paperComment(@PathVariable @NotNull String id,
                                             @RequestParam @NotNull String researchId,
                                             @RequestParam @NotNull String comment,
                                             @RequestAttribute("user") User user) {

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            paperService.commentPaper(savedPaper, comment);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    @PutMapping("/{id}/save-state")
    public ResponseEntity<Void> paperSaveState(@PathVariable @NotNull String id,
                                               @RequestParam @NotNull String researchId,
                                               @RequestParam @NotNull SaveState saveState,
                                               @RequestAttribute("user") User user) {

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            paperService.changeSaveState(savedPaper, saveState);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}/relevance")
    public ResponseEntity<Void> paperRelevance(@PathVariable @NotNull String id,
                                               @RequestParam @NotNull String researchId,
                                               @RequestParam int relevance,
                                               @RequestAttribute("user") User user) {

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            paperService.setRelevanceOfPaper(savedPaper, relevance);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    @PostMapping("/{id}/recommendations")
    public ResponseEntity<Map<String, List<Paper>>> paperRecommendations(@PathVariable String id,
                                                  @RequestParam @NotNull String researchId,
                                                  @RequestParam @NotNull RecommendationMethod method,
                                                   @RequestBody @NotNull Map<String, List<OrganizerRequest>> request,
                                               @RequestAttribute("user") User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());
        OrganizerList<Paper> organizerList = OrganizerList.createFromOrganizerRequests(organizers);

        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            List<Paper> papers = switch (method) {
                case ALGORITHM -> recommendationService.getRecommendations(List.of(paper), List.of());
                case CITATIONS -> recommendationService.getCitations(research, List.of(paper)).stream()
                        .map(cachedPaper -> cachedPaper.getCachedPaperId().getPaper()).toList();
                case REFERENCES -> recommendationService.getReferences(research, List.of(paper)).stream()
                        .map(cachedPaper -> cachedPaper.getCachedPaperId().getPaper()).toList();
            };

            papers = organizerList.organize(papers);
            return ResponseEntity.ok(Map.of("recommendations", papers));
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper not owned by user");
        }

    }
}

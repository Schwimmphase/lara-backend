package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
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

/**
 * This class contains all the rest api endpoints for papers and saved papers.
 *
 * @author unqkm
 */
@RestController
@RequestMapping("/paper")
@RequiredArgsConstructor
public class PaperController {

    private final ApiActionController apiActionController;
    private final PaperService paperService;
    private final ResearchService researchService;
    private final TagService tagService;

    /**
     * Get all information about a paper. If the researchId is given, the saved paper is returned and if not a paper
     * from the api is returned.
     *
     * @param id         id of the paper.
     * @param researchId id of the research.
     * @param user       user that has sent the request.
     * @return           the response body containing the paper with the status code 200. If the paper is not owned by
     *                   the user, the status code 403 is returned. If the paper is not found, the status code 400 is
     *                   returned.
     */
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

    /**
     * Add ad tag to a saved paper.
     *
     * @param id         id of the paper.
     * @param researchId id of the research.
     * @param tagId      id of the tag.
     * @param user       user that has sent the request.
     * @return           the empty response body with the status code 200. If the paper is not owned by the user, the
     *                   status code 403 is returned. If the paper is not found, the status code 400 is returned. If the
     *                   paper already has this tag, the status code 409 is returned.
     */
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

            if (savedPaper.getTags().contains(tag)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Paper already has this tag");
            }

            paperService.addTagToPaper(savedPaper, tag);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper or tag not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    /**
     * Remove a tag from a saved paper.
     *
     * @param id         id of the paper.
     * @param researchId id of the research.
     * @param tagId      id of the tag.
     * @param user       user that has sent the request.
     * @return           the empty response body with the status code 200. If the paper is not owned by the user, the
     *                   status code 403 is returned. If the paper is not found, the status code 400 is returned. If the
     *                   paper does not have this tag, the status code 409 is returned.
     */
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

            if (!savedPaper.getTags().contains(tag)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Paper does not has this tag");
            }

            paperService.removeTagFromPaper(savedPaper, tag);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper or tag not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    /**
     * Add a comment to a saved paper.
     *
     * @param id             id of the paper.
     * @param researchId     id of the research.
     * @param commentRequest request body containing the comment.
     * @param user           user that has sent the request.
     * @return               the empty response body with the status code 200. If the paper is not owned by the user, the
     *                       status code 403 is returned. If the paper is not found, the status code 400 is returned.
     */
    @PatchMapping("/{id}/comment")
    public ResponseEntity<Void> paperComment(@PathVariable @NotNull String id,
                                             @RequestParam @NotNull String researchId,
                                             @RequestBody @NotNull Map<String, String> commentRequest,
                                             @RequestAttribute("user") User user) {
        String comment = commentRequest.get("comment");
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

    /**
     * Change the save state of a paper.
     *
     * @param id         id of the paper.
     * @param researchId id of the research.
     * @param saveState  new save state.
     * @param user       user that has sent the request.
     * @return           the empty response body with the status code 200. If the paper is not owned by the user, the
     *                   status code 403 is returned. If the paper is not found, the status code 400 is returned.
     */
    @PutMapping("/{id}/save-state")
    public ResponseEntity<Void> paperSaveState(@PathVariable @NotNull String id,
                                               @RequestParam @NotNull String researchId,
                                               @RequestParam("save-state") @NotNull SaveState saveState,
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
        }
    }

    /**
     * Change the relevance of a paper.
     *
     * @param id         id of the paper.
     * @param researchId id of the research.
     * @param relevance  new relevance.
     * @param user       user that has sent the request.
     * @return           the empty response body with the status code 200. If the paper is not owned by the user, the
     *                   status code 403 is returned. If the paper is not found, the status code 400 is returned.
     */
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

    @PutMapping("/{id}/user-pdf")
    public ResponseEntity<Void> setUserPdf(@PathVariable @NotNull String id,
                                           @RequestParam @NotNull String researchId,
                                           @RequestParam @NotNull String url,
                                           @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(id);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);
            paperService.setUserPdfUrl(savedPaper, url);
            return ResponseEntity.ok().build();
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Paper not owned by user");
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }

    /**
     * Get recommendations for a paper. This is also used to get the citations and references of a paper.
     *
     * @param id      id of the paper.
     * @param method  method to use for the recommendation. Allowed values are "algorithm", "citations" and
     *                "references".
     * @param request request body containing the organizers.
     * @return        a list of papers. If the paper is not found, the status code 400 is returned.
     */
    @PostMapping("/{id}/recommendations")
    public ResponseEntity<Map<String, List<Paper>>> paperRecommendations(@PathVariable String id,
                                                                         @RequestParam @NotNull RecommendationMethod method,
                                                                         @RequestAttribute("user") User user,
                                                                         @RequestBody @NotNull Map<String, List<OrganizerRequest>> request) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());
        OrganizerList<Paper> organizerList = OrganizerList.createFromOrganizerRequests(organizers);

        try {
            Paper paper = paperService.getPaper(id, true);
            List<Paper> papers = switch (method) {
                case ALGORITHM -> apiActionController.getRecommendations(List.of(paper), List.of());
                case CITATIONS -> apiActionController.getCitations(paper);
                case REFERENCES -> apiActionController.getReferences(paper);
            };

            List<Paper> hiddenPapers = researchService.getHiddenPapers(user.getActiveResearch());
            papers.removeIf(hiddenPapers::contains);
            papers = organizerList.organize(papers);


            return ResponseEntity.ok(Map.of("recommendations", papers));
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}


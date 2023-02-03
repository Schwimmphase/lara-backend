package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.request.ResearchRequest;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/research")
@RequiredArgsConstructor
public class ResearchController {

    private final ResearchService researchService;
    private final PaperService paperService;
    private final TagService tagService;

    @PostMapping("/")
    public ResponseEntity<Research> createResearch(@RequestBody ResearchRequest request, @RequestAttribute("user") User user) {
        Research research = researchService.createResearch(user, request.title(), request.description());

        return ResponseEntity.ok(research);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Research>>> listResearch(@RequestAttribute("user") User user) {
        List<Research> researches = researchService.getResearches(user);

        return ResponseEntity.ok(Map.of("researches", researches));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Research> updateResearch(@PathVariable("id") String researchId,
                                                   @RequestBody ResearchRequest request, @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            researchService.updateResearch(research, request.title(), request.description());
            return ResponseEntity.ok(research);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResearch(@PathVariable("id") String researchId, @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            researchService.deleteResearch(research);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @PutMapping("/{id}/paper")
    public ResponseEntity<Void> savePaper(@PathVariable("id") String researchId, @RequestParam String paperId,
                                @RequestParam("state") SaveState saveState, @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(paperId);
            paperService.createSavedPaper(research, paper, saveState);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research or paper with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research or paper not owned by user");
        }
    }

    @DeleteMapping("/{id}/paper")
    public ResponseEntity<Void> deletePaper(@PathVariable("id") String researchId, @RequestParam String paperId,
                                            @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            Paper paper = paperService.getPaper(paperId);
            SavedPaper savedPaper = paperService.getSavedPaper(user, paper, research);

            paperService.removeSavedPaper(research, savedPaper);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research or paper with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research or paper not owned by user");
        }
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<Map<String, List<Tag>>> researchTags(@PathVariable("id") String researchId,
                                                               @RequestAttribute("user") User user) {
        try {
            Research research = researchService.getResearch(researchId, user);
            List<Tag> tags = tagService.getTags(research);
            return ResponseEntity.ok(Map.of("tags", tags));
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @PostMapping("/{id}/papers")
    public ResponseEntity<Map<String, List<SavedPaper>>> researchPapers(@PathVariable("id") String researchId,
                                                                   @RequestBody Map<String, List<OrganizerRequest>> request,
                                                                   User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        try {
            Research research = researchService.getResearch(researchId, user);
            List<SavedPaper> papers = paperService.getSavedPapers(research, user);
            return ResponseEntity.ok(Map.of("papers", papers));
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @PostMapping("/{id}/recommendations")
    public ResponseEntity<Map<String, List<Paper>>> researchRecommendations(@PathVariable("id") String researchId,
                                                                            @RequestParam RecommendationMethod method,
                                                                            @RequestBody Map<String, List<OrganizerRequest>> request,
                                                                            User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        try {
            Research research = researchService.getResearch(researchId, user);

            List<Paper> researchPapers = paperService.getSavedPapers(research, user).stream()
                    .map(savedPaper -> savedPaper.getSavedPaperId().getPaper()).toList();

            List<Paper> papers = switch (method) {
                case ALGORITHM -> researchService.getRecommendations(research);
                case CITATIONS -> researchService.getCitations(research, researchPapers).stream()
                        .map(cachedPaper -> cachedPaper.getCachedPaperId().getPaper()).toList();
                case REFERENCES -> researchService.getReferences(research, researchPapers).stream()
                        .map(cachedPaper -> cachedPaper.getCachedPaperId().getPaper()).toList();
            };
            return ResponseEntity.ok(Map.of("papers", papers));
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research with this id not found");
        } catch (WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, List<Paper>>> researchSearch(@RequestParam String query,
                                                                   @RequestBody Map<String, List<OrganizerRequest>> request,
                                                                   User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        try {
            List<Paper> papers = researchService.searchByQuery(query);

            return ResponseEntity.ok(Map.of("papers", papers));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paper")
public class PaperController {

    @GetMapping("{id}")
    public ResponseEntity<Paper> paperDetails(@PathVariable String id, User user) {

        // TODO: replace mock with code
        Author author = new Author("mockId", "mockName");
        Paper paper = new Paper("1234567890", "thePaper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));

        return ResponseEntity.ok(paper);
    }

    @PutMapping("{id}/tag")
    public HttpStatus paperAddTag(@PathVariable String id, @RequestParam String researchId, @RequestParam String tagId,
                                  User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}/tag")
    public HttpStatus paperTagRemove(@PathVariable String id, @RequestParam String researchId,
                                     @RequestParam String tagId, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @PatchMapping("{id}/comment")
    public HttpStatus paperComment(@PathVariable String id, @RequestParam String researchId,
                                   @RequestParam String comment, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @PutMapping("{id}/save-state")
    public HttpStatus paperSaveState(@PathVariable String id, @RequestParam String researchId,
                                     @RequestParam SaveState saveState, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @PatchMapping("{id}/relevance")
    public HttpStatus paperRelevance(@PathVariable String id, @RequestParam String researchId,
                                     @RequestParam int relevance, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @PostMapping("{id}/recommendations")
    public ResponseEntity<Map<String, List<Paper>>> paperRecommendations(@PathVariable String id,
                                                           @RequestParam String researchId,
                                                            @RequestBody Map<String, List<OrganizerRequest>> request,
                                                                         User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        // TODO: replace mock with code
        List<Paper> papers = new ArrayList<>();
        Author author = new Author("mockId", "mockName");
        papers.add(new Paper("1234567890", "thePaper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author)));
        return ResponseEntity.ok(Map.of("recommendations", papers));
    }
}

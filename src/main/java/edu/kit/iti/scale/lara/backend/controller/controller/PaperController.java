package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/paper")
public class PaperController {

    @GetMapping("{id}")
    public ResponseEntity<Object> paperDetails(@PathVariable String id, @PathVariable(required = false) String researchId,
                                                User user) {

        // TODO: replace mock with code
        Author author = new Author("mockId", "mockName");
        Paper paper = new Paper("1234567890", "thePaper", 2023, "abstract",
                0, 0, "venue", "https://arxiv.org/pdf/2110.11697.pdf", List.of(author));

        if (researchId == null) {
            return ResponseEntity.ok(paper);
        } else {
            UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
            User user1 = new User("one","11111", "password1", category);
            Research research = new Research(researchId, "randomResearch", new Comment("12345", "text"),
                    ZonedDateTime.now(), user1);
            return ResponseEntity.ok(new SavedPaper(paper, research, SaveState.SAVED));
        }

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

package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
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

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/paper")
public class PaperController {

    @GetMapping("{id}")
    public ResponseEntity<Paper> paperDetails(@PathVariable String id, User user) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper = new Paper("1234567890", "thePaper", 2023, "abstract",
                0, 0, "venue", "url", author);

        return ResponseEntity.ok(paper);
    }

    @PutMapping("{id}/tag")
    public HttpStatus paperAddTag(@PathVariable String id, @RequestParam String researchId, @RequestParam String tagId,
                                  User user) {

        //mock
        return HttpStatus.OK;
    }

    @DeleteMapping("{id}/tag")
    public HttpStatus paperTagRemove(@PathVariable String id, @RequestParam String researchId,
                                     @RequestParam String tagId, User user) {

        //mock
        return HttpStatus.OK;
    }

    @PatchMapping("{id}/comment")
    public HttpStatus paperComment(@PathVariable String id, @RequestParam String researchId,
                                   @RequestParam String comment, User user) {

        //mock
        return HttpStatus.OK;
    }

    @PutMapping("{id}/save-state")
    public HttpStatus paperSaveState(@PathVariable String id, @RequestParam String researchId,
                                     @RequestParam SaveState saveState, User user) {

        //mock
        return HttpStatus.OK;
    }

    @PatchMapping("{id}/relevance")
    public HttpStatus paperRelevance(@PathVariable String id, @RequestParam String researchId,
                                     @RequestParam int relevance, User user) {

        //mock
        return HttpStatus.OK;
    }

    @PostMapping("{id}/recommendations")
    public ResponseEntity<List<Paper>> paperRecommendations(@PathVariable String id, @RequestParam String researchId,
                                                            @RequestBody List<OrganizerRequest> organizers, User user) {

        //mock
        List<Paper> papers = new ArrayList<>();
        Author author = new Author("mockId", "mockName");
        papers.add(new Paper("1234567890", "thePaper", 2023, "abstract",
                0, 0, "venue", "url", author));
        return ResponseEntity.ok(papers);
    }
}

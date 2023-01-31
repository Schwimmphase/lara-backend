package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.request.ResearchRequest;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
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
@RequestMapping("/research")
public class ResearchController {

    @PostMapping("/")
    public ResponseEntity<Research> createResearch(@RequestBody ResearchRequest request, User user) {

        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "New-Research", new Comment("12345", "description"), ZonedDateTime.now(), user);

        return ResponseEntity.ok(research);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Research>>> listResearch(User user) {

        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research1 = new Research("12345", "Research1", new Comment("12345", "description"), ZonedDateTime.now(), user);
        Research research3 = new Research("12345", "Research3", new Comment("12345", "description"), ZonedDateTime.now(), user);
        Research research2 = new Research("12345", "Research2", new Comment("12345", "description"), ZonedDateTime.now(), user);

        List<Research> researches = new ArrayList<>();
        researches.add(research1);
        researches.add(research2);
        researches.add(research3);

        return ResponseEntity.ok(Map.of("researches", researches));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Research> updateResearch(@PathVariable("id") String researchId,
                                                   @RequestBody ResearchRequest request, User user) {

        // TODO: replace mock with code
        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "UpdatedResearch", new Comment("12345", "description"), ZonedDateTime.now(), user1);
        return ResponseEntity.ok(research);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteResearch(@PathVariable("id") String researchId, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @PutMapping("/{id}/paper")
    public HttpStatus savePaper(@PathVariable("id") String researchId, @RequestParam String paperId,
                                @RequestParam("state") SaveState saveState, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}/paper")
    public HttpStatus deletePaper(@PathVariable("id") String researchId, @RequestParam String paperId, User user) {

        // TODO: replace mock with code
        return HttpStatus.OK;
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<Map<String, List<Tag>>> researchTags(@PathVariable("id") String researchId, User user) {

        // TODO: replace mock with code
        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "randomResearch", new Comment("12345", "text"), ZonedDateTime.now(), user1);
        Tag tag1 = new Tag("11111", "#0000FF", "New-Tag1", research);
        Tag tag2 = new Tag("22222", "#0000FF", "New-Tag2", research);
        Tag tag3 = new Tag("33333", "#0000FF", "New-Tag3", research);
        Tag tag4 = new Tag("44444", "#0000FF", "New-Tag4", research);

        List<Tag> tags = new ArrayList<>();

        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);

        return ResponseEntity.ok(Map.of("tags", tags));
    }

    @PostMapping("/{id}/papers")
    public ResponseEntity<Map<String, List<SavedPaper>>> researchPapers(@PathVariable("id") String researchId,
                                                                        @RequestBody Map<String, List<OrganizerRequest>> request,
                                                                        User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        // TODO: replace mock with code
        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);
        Research research = new Research("12345", "randomResearch", new Comment("12345", "text"),
                ZonedDateTime.now(), user1);
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "resPaper1", 2023, "abstract1",
                1, 1, "venue1", "url1", List.of(author));
        Paper paper2 = new Paper("222222", "resPaper2", 2023, "abstract2",
                2, 2, "venue2", "url2", List.of(author));
        Paper paper3 = new Paper("333333", "resPaper3", 2023, "abstract3",
                3, 3, "venue3", "url3", List.of(author));

        SavedPaper savedPaper1 = new SavedPaper(paper1, research, SaveState.SAVED);
        SavedPaper savedPaper2 = new SavedPaper(paper2, research, SaveState.SAVED);
        SavedPaper savedPaper3 = new SavedPaper(paper3, research, SaveState.SAVED);

        List<SavedPaper> savedPapers = new ArrayList<>();
        savedPapers.add(savedPaper1);
        savedPapers.add(savedPaper2);
        savedPapers.add(savedPaper3);

        return ResponseEntity.ok(Map.of("savedPapers", savedPapers));
    }

    @PostMapping("/{id}/recommendations")
    public ResponseEntity<Map<String, List<Paper>>> researchRecommendations(@PathVariable("id") String researchId,
                                                               @RequestParam RecommendationMethod method,
                                                               @RequestBody Map<String, List<OrganizerRequest>> request,
                                                               User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        // TODO: replace mock with code
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "recPaper1", 2023, "abstract1",
                1, 1, "venue1", "url1", List.of(author));
        Paper paper2 = new Paper("222222", "recPaper2", 2023, "abstract2",
                2, 2, "venue2", "url2", List.of(author));
        Paper paper3 = new Paper("333333", "recPaper3", 2023, "abstract3",
                3, 3, "venue3", "url3", List.of(author));
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return ResponseEntity.ok(Map.of("recommendations", papers));
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, List<Paper>>> researchSearch(@RequestParam String query,
                                                      @RequestBody Map<String, List<OrganizerRequest>> request,
                                                      User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        // TODO: replace mock with code
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "Paper1", 2023, "abstract1",
                1, 1, "venue1", "url1", List.of(author));
        Paper paper2 = new Paper("222222", "Paper2", 2023, "abstract2",
                2, 2, "venue2", "url2", List.of(author));
        Paper paper3 = new Paper("333333", "Paper3", 2023, "abstract3",
                3, 3, "venue3", "url3", List.of(author));
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return ResponseEntity.ok(Map.of("papers", papers));
    }
}

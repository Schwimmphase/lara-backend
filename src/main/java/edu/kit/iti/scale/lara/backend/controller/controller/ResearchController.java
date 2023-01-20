package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.request.ResearchRequest;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ResearchController {

    public ResponseEntity<Research> createResearch(ResearchRequest request, User user) {

        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "New-Research", new Comment("12345", "description"), ZonedDateTime.now(), user);

        return ResponseEntity.ok(research);
    }

    public List<Research> listResearch(User user) {

        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research1 = new Research("12345", "Research1", new Comment("12345", "description"), ZonedDateTime.now(), user);
        Research research3 = new Research("12345", "Research3", new Comment("12345", "description"), ZonedDateTime.now(), user);
        Research research2 = new Research("12345", "Research2", new Comment("12345", "description"), ZonedDateTime.now(), user);

        List<Research> researches = new ArrayList<>();
        researches.add(research1);
        researches.add(research2);
        researches.add(research3);

        return researches;
    }

    public ResponseEntity<Research> updateResearch(String researchId, ResearchRequest request, User user) {

        //mock
        UserCategory category = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user1 = new User("one","11111", "password1", category);

        Research research = new Research("12345", "UpdatedResearch", new Comment("12345", "description"), ZonedDateTime.now(), user1);
        return null;
    }

    public HttpStatus deleteResearch(String researchId, User user) {

        //mock
        return HttpStatus.OK;
    }

    public HttpStatus savePaper(String researchId, String paperId, SaveState saveState, User user) {

        //mock
        return HttpStatus.OK;
    }

    public HttpStatus deletePaper(String researchId, String paperId, User user) {

        //mock
        return HttpStatus.OK;
    }

    public ResponseEntity<List<Tag>> researchTags(String researchId, User user) {

        //mock
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

        return ResponseEntity.ok(tags);
    }

    public ResponseEntity<List<Paper>> researchPapers(String researchId, List<OrganizerRequest> requestList, User user) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "resPaper1", 2023, "abstract1",
                1, 1, "venue1", "url1", author);
        Paper paper2 = new Paper("222222", "resPaper2", 2023, "abstract2",
                2, 2, "venue2", "url2", author);
        Paper paper3 = new Paper("333333", "resPaper3", 2023, "abstract3",
                3, 3, "venue3", "url3", author);

        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return ResponseEntity.ok(papers);
    }

    public ResponseEntity<List<Paper>> researchRecommendations(String researchId, RecommendationMethod method, List<OrganizerRequest> requestList, User user) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "recPaper1", 2023, "abstract1",
                1, 1, "venue1", "url1", author);
        Paper paper2 = new Paper("222222", "recPaper2", 2023, "abstract2",
                2, 2, "venue2", "url2", author);
        Paper paper3 = new Paper("333333", "recPaper3", 2023, "abstract3",
                3, 3, "venue3", "url3", author);
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return ResponseEntity.ok(papers);
    }

    public ResponseEntity<List<Paper>> researchSearch(String query, List<OrganizerRequest> requestList, User user) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "Paper1", 2023, "abstract1",
                1, 1, "venue1", "url1", author);
        Paper paper2 = new Paper("222222", "Paper2", 2023, "abstract2",
                2, 2, "venue2", "url2", author);
        Paper paper3 = new Paper("333333", "Paper3", 2023, "abstract3",
                3, 3, "venue3", "url3", author);
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return ResponseEntity.ok(papers);
    }
}

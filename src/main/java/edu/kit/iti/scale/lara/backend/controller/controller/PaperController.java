package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaperController {

    public ResponseEntity<Paper> paperDetails(String id, User user) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper = new Paper("1234567890", "thePaper", 2023, "abstract",
                0, 0, "venue", "https://arxiv.org/pdf/2110.11697.pdf", author);

        return ResponseEntity.ok(paper);
    }

    public HttpStatus paperAddTag(String id, String tagId, User user) {

        //mock
        return HttpStatus.OK;
    }

    public HttpStatus paperTagRemove(String id, String tagId, User user) {

        //mock
        return HttpStatus.OK;
    }

    public HttpStatus paperComment(String id, String comment, User user) {

        //mock
        return HttpStatus.OK;
    }

    public HttpStatus paperSaveState(String id, SaveState saveState, User user) {

        //mock
        return HttpStatus.OK;
    }

    public HttpStatus paperRelevance(String id, int relevance, User user) {

        //mock
        return HttpStatus.OK;
    }
}

package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
public class BibtexExportController {

    @PostMapping("/research/{researchId}")
    public void exportResearch(@PathVariable String researchId, User user) {

        // TODO

    }

    @PostMapping("/paper/{paperId}")
    public void exportPaper(@PathVariable String paperId, User user) {

        // TODO

    }
}

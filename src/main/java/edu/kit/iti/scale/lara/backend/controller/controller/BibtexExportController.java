package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/export")
public class BibtexExportController {

    @PostMapping("/research/{researchId}")
    public ResponseEntity<Map<String, String>> exportResearch(@PathVariable String researchId, Map<String, List<OrganizerRequest>> request, User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());

        // TODO: replace mock with code
        return ResponseEntity.ok(Map.of("export", "@article{article, author = {Peter Adams}, " +
                "title = {The title of the work}, journal = {The name of the journal}, year = 1993, number = 2, " +
                "pages = {201-213}, month = 7, note = {An optional note}, volume = 4 }"));
    }

    @PostMapping("/paper/{paperId}")
    public ResponseEntity<Map<String, String>> exportPaper(@PathVariable String paperId, User user) {

        // TODO: replace mock with code
        return ResponseEntity.ok(Map.of("export", "@article{article, author = {Peter Adams}, " +
                "title = {The title of the work}, journal = {The name of the journal}, year = 1993, number = 2, " +
                "pages = {201-213}, month = 7, note = {An optional note}, volume = 4 }"));
    }
}

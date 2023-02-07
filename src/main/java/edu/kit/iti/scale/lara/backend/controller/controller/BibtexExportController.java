package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.service.BibtexConverterService;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.organizer.OrganizerList;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class BibtexExportController {

    private final BibtexConverterService bibtexConverterService;
    private final ResearchService researchService;
    private final PaperService paperService;

    @PostMapping("/research/{researchId}")
    public ResponseEntity<Map<String, String>> exportResearch(@PathVariable @NotNull String researchId,
                                        @RequestBody @NotNull Map<String, List<OrganizerRequest>> request,
                                    @RequestAttribute("user") User user) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());
        OrganizerList<Paper> organizerList = OrganizerList.createFromOrganizerRequests(organizers);

        try {
            Research research = researchService.getResearch(researchId, user);
            List<Paper> papers = paperService.getSavedPapers(research, user).stream()
                    .map(savedPaper -> savedPaper.getSavedPaperId().getPaper()).toList();
            papers = organizerList.organize(papers);
            return ResponseEntity.ok(Map.of("export", bibtexConverterService.export(papers)));
        } catch (NotInDataBaseException | WrongUserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Research not owned by user");
        }
    }

    @GetMapping("/paper/{paperId}")
    public ResponseEntity<Map<String, String>> exportPaper(@PathVariable String paperId,
                                                           @RequestAttribute("user") User user) {

        try {
            List<Paper> papers = List.of(paperService.getPaper(paperId));
            return ResponseEntity.ok(Map.of("export", bibtexConverterService.export(papers)));
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paper with this id not found");
        }
    }
}

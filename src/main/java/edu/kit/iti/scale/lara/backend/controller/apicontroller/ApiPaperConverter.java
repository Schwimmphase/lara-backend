package edu.kit.iti.scale.lara.backend.controller.apicontroller;


import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

public class ApiPaperConverter {

    public edu.kit.iti.scale.lara.backend.model.research.paper.Paper convert(ApiPaper paper) {
        return new Paper(paper.getId(), paper.getTitle(), paper.getYear(), paper.getAbstractText(), paper.getCitationCount(), paper.getReferenceCount(), paper.getVenue(), paper.getPdfUrl(), paper.getAuthors());
    }

}

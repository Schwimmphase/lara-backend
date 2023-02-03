package edu.kit.iti.scale.lara.backend.controller.apicontroller;


import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

public class ApiPaperConverter {

    public edu.kit.iti.scale.lara.backend.model.research.paper.Paper convert(ApiPaper paper) {
        return new Paper(paper.id(), paper.title(), paper.year(), paper.abstractText(), paper.citationCount(), paper.referenceCount(), paper.venue(), paper.pdfUrl(), paper.authors());
    }

}

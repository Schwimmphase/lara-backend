package edu.kit.iti.scale.lara.backend.controller.apicontroller;


import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

public class ApiPaperConverter {

    public Paper convert(ApiPaper apiPaper) {
        return new Paper(apiPaper.getId(), apiPaper.getTitle(), apiPaper.getYear(), apiPaper.getAbstractText(),
                apiPaper.getCitationCount(), apiPaper.getReferenceCount(), apiPaper.getVenue(), apiPaper.getPdfUrl(),
                apiPaper.getAuthor());
    }

}

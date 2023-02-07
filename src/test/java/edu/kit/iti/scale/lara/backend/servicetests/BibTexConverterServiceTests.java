package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import edu.kit.iti.scale.lara.backend.api.semanticscolar.SemanticScholarHandler;
import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.service.BibtexConverterService;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

@InMemoryTest
public class BibTexConverterServiceTests {
    @Autowired
    private BibtexConverterService bibtexConverterService;
    @Autowired
    private ApiActionController apiActionController;

    @Test
    public void testExport() {
        try {
            List<Paper> papers = apiActionController.getPapersByKeyword("Thomas Bläsius");

            String bibTex = bibtexConverterService.export(papers);
            System.out.println(bibTex);

        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
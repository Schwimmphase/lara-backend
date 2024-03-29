package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SemanticScholarHandlerTest {

    @Test
    void getPapersByKeyword() throws IOException {

        // set up
        String query = "graph";

        // execution
        List<ApiPaper> results = new SemanticScholarHandler().getPapersByKeyword(query);

        System.out.println(results.size());

        // test
        assertNotNull(results);

        for (ApiPaper paper : results) {
            assertNotNull(paper.id());
            assertNotNull(paper.authors());
            //assertNotNull(paper.getAbstractText());
            assertNotNull(paper.title());
            assertNotNull(paper.venue());
            //assertNotNull(paper.pdfUrl());
        }
    }

    @Test
    void getRecommendations() throws IOException {

        // set up
        List<String> positiveIds = List.of("S2$385742fffcf113656f0d3cf6c06ef95cb8439dc6");
        List<String> negativeIds = List.of("S2$e24cdf73b3e7e590c2fe5ecac9ae8aa983801367");


        // execution
        List<ApiPaper> results = new SemanticScholarHandler().getRecommendations(positiveIds, negativeIds);

        // test
        for (ApiPaper paper : results) {
            assertNotNull(paper.id());
            assertNotNull(paper.authors());
            //assertNotNull(paper.getAbstractText());
            assertNotNull(paper.title());
            assertNotNull(paper.venue());
            //assertNotNull(paper.pdfUrl());
        }

    }

    @Test
    void getCitations() throws IOException {
        // set up
        String paperId = "S2$385742fffcf113656f0d3cf6c06ef95cb8439dc6";

        // execution
        List<ApiPaper> results = new SemanticScholarHandler().getCitations(paperId);

        // test
        for (ApiPaper paper : results) {
            assertNotNull(paper.id());
            assertNotNull(paper.authors());
            //assertNotNull(paper.getAbstractText());
            assertNotNull(paper.title());
            assertNotNull(paper.venue());
            //assertNotNull(paper.pdfUrl());
        }
    }

    @Test
    void getReferences() throws IOException {
        // set up
        String paperId = "S2$649def34f8be52c8b66281af98ae884c09aef38b";

        // execution
        List<ApiPaper> results = new SemanticScholarHandler().getReferences(paperId);

        // test
        for (ApiPaper paper : results) {
            assertNotNull(paper.id());
            assertNotNull(paper.authors());
            //assertNotNull(paper.getAbstractText());
            assertNotNull(paper.title());
            assertNotNull(paper.venue());
            //assertNotNull(paper.pdfUrl());
        }
    }

    @Test
    void getPaper() throws IOException {
        // set up
        String paperId = "S2$649def34f8be52c8b66281af98ae884c09aef38b";

        // execution
        List<ApiPaper> results = List.of(new SemanticScholarHandler().getPaper(paperId));

        // test
        for (ApiPaper paper : results) {
            assertNotNull(paper.id());
            assertNotNull(paper.authors());
            assertNotNull(paper.abstractText());
            assertNotNull(paper.title());
            assertNotNull(paper.venue());
            assertNotNull(paper.pdfUrl());
        }
    }
    @Test
    void testArXivPdf() {
        try {
            List<ApiPaper> papers = new SemanticScholarHandler().getPapersByKeyword("Joint scattering Environment");
            for (ApiPaper paper : papers) {
                System.out.println(paper.pdfUrl());
            }
        } catch (IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
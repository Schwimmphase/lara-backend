package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SemanticScholarHandlerTest {

    @Test
    void getPapersByKeyword() throws IOException {

        // set up
        String query = "graph algorithm";

        // execution
        List<ApiPaper> results = new SemanticScholarHandler().getPapersByKeyword(query);

        System.out.println(results.size());

        // test
        assertNotNull(results);

        for (ApiPaper paper : results) {
            assertNotNull(paper.getId());
            assertNotNull(paper.getAuthors());
            assertNotNull(paper.getAbstractText());
            assertNotNull(paper.getTitle());
            assertNotNull(paper.getVenue());
            assertNotNull(paper.getPdfUrl());
        }
    }

    @Test
    void getRecommendations() throws IOException {

        // set up
        List<String> positiveIds = List.of("SemSchol#385742fffcf113656f0d3cf6c06ef95cb8439dc6");
        List<String> negativeIds = List.of("SemSchol#e24cdf73b3e7e590c2fe5ecac9ae8aa983801367");


        // execution
        List<ApiPaper> results = new SemanticScholarHandler().getRecommendations(positiveIds, negativeIds);

        // test
        System.out.println(results.size());

    }


}
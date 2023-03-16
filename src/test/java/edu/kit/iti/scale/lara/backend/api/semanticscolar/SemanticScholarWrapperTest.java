package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SemanticScholarWrapperTest {

    String jsonPaper = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getResource("/semantic-scholar/paper.json")).toURI())));

    String jsonReferences = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getResource("/semantic-scholar/references.json")).toURI())));

    String jsonCitations = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getResource("/semantic-scholar/citations.json")).toURI())));

    String jsonRecommendations = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(getClass().getResource("/semantic-scholar/recommendations.json")).toURI())));

    SemanticScholarWrapperTest() throws IOException, URISyntaxException {
    }

    @Test
    void convertRecommendations() throws JsonProcessingException, JSONException {

        // execution
        List<ApiPaper> apiPapers = new SemanticScholarWrapper().convertRecommendationsSearchResults(jsonRecommendations, "recommendedPapers");

        // test
        assertNotNull(apiPapers);

        for (ApiPaper paper : apiPapers) {
            assertNotNull(paper.title());
            assertNotNull(paper.id());
            assertNotNull(paper.venue());
            assertNotNull(paper.abstractText());
            assertNotNull(paper.authors());
        }
    }

    @Test
    void convertCitationsReferences() throws JSONException, JsonProcessingException {

        // test references
        List<ApiPaper> references = new SemanticScholarWrapper().convertCitationsReferencesSearch(jsonReferences, "citedPaper");

        assertNotNull(references);

        for (ApiPaper paper : references) {
            assertNotNull(paper.title());
            assertNotNull(paper.id());
            assertNotNull(paper.venue());
            assertNotNull(paper.authors());
        }

        // test citations
        List<ApiPaper> citations = new SemanticScholarWrapper().convertCitationsReferencesSearch(jsonCitations, "citingPaper");

        assertNotNull(citations);

        for (ApiPaper paper : citations) {
            assertNotNull(paper.title());
            assertNotNull(paper.id());
            assertNotNull(paper.venue());
            assertNotNull(paper.authors());
        }
    }

    @Test
    void convertToPaper() throws JSONException, JsonProcessingException {
        List<ApiPaper> paper = new SemanticScholarWrapper().convertToPaper(jsonPaper);
        for (int i = 0; i < paper.get(0).authors().size(); i++) {
            assertNotNull(paper.get(0).authors().get(i).getName());
            assertNotNull(paper.get(0).authors().get(i).getId());
        }
        assertEquals(1, paper.size());
        assertNotNull(paper.get(0).id());
        assertNotNull(paper.get(0).title());
        assertNotNull(paper.get(0).pdfUrl());
        assertNotNull(paper.get(0).venue());
        assertNotNull(paper.get(0).abstractText());
    }

    @Test
    void convertToPaperNullId() throws JSONException, JsonProcessingException {
        String jsonPaper = "{\"paperId\": null, \"title\": \"test-title\", \"abstract\": \"test-abstract\", \"venue\": \"test-venue\", \"year\": 42069, \"referenceCount\": 69, \"citationCount\": 69, \"openAccessPdf\": null, \"authors\": []}";
        List<ApiPaper> apiPapers = new SemanticScholarWrapper().convertToPaper(jsonPaper);
        assertEquals(List.of(), apiPapers);
    }
}
package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;

import java.util.List;

public record SemanticScholarPaper(
        @JsonProperty("authors")
        List<Author> authors,
        @JsonProperty("paperId")
        String id,
        @JsonProperty("title")
        String title,
        @JsonProperty("year")
        int year,
        @JsonProperty("abstract")
        String abstractText,
        @JsonProperty("citationCount")
        int citationCount,
        @JsonProperty("referenceCount")
        int referenceCount,
        @JsonProperty("venue")
        String venue,
        @JsonProperty("openAccessPdf")
        OpenAccessPdf openAccessPdf) {

    public record OpenAccessPdf(@JsonProperty("url") String url, @JsonProperty("status") String status) {

    }

}

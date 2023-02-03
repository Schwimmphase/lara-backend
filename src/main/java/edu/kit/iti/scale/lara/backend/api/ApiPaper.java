package edu.kit.iti.scale.lara.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ApiPaper {

    @JsonProperty("authors")
    private List<Author> authors;
    @JsonProperty("paperId")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("year")
    private int year;
    @JsonProperty("abstract")
    private String abstractText;
    @JsonProperty("citationCount")
    private int citationCount;
    @JsonProperty("referenceCount")
    private int referenceCount;
    @JsonProperty("venue")
    private String venue;
    @JsonProperty("url")
    private String pdfUrl;
}

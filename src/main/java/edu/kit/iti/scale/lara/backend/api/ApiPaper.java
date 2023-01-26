package edu.kit.iti.scale.lara.backend.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;

import java.util.List;

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

    public List<Author> getAuthors() {
        return authors;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public int getCitationCount() {
        return citationCount;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public String getVenue() {
        return venue;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public void setCitationCount(int citationCount) {
        this.citationCount = citationCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

}

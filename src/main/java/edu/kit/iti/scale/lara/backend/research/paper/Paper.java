package edu.kit.iti.scale.lara.backend.research.paper;

import edu.kit.iti.scale.lara.backend.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.research.paper.savedpaper.SavedPaper;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "papers", schema = "lara")
public class Paper {
    @Id
    private String id;
    private String title;
    private int year;
    private String abstractText;
    private int citationCount;
    private int referenceCount;
    private String venue;
    private String pdfUrl;
    @OneToMany(mappedBy = "paper")
    private List<CachedPaper> cachedPapers;
    @OneToMany(mappedBy = "paper")
    private List<SavedPaper> savedPapers;
    @ManyToOne
    private Author author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public int getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(int citationCount) {
        this.citationCount = citationCount;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public List<CachedPaper> getCachedPapers() {
        return cachedPapers;
    }

    public void setCachedPapers(List<CachedPaper> cachedPapers) {
        this.cachedPapers = cachedPapers;
    }

    public List<SavedPaper> getSavedPapers() {
        return savedPapers;
    }

    public void setSavedPapers(List<SavedPaper> savedPapers) {
        this.savedPapers = savedPapers;
    }
}

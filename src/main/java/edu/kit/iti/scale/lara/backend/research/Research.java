package edu.kit.iti.scale.lara.backend.research;

import edu.kit.iti.scale.lara.backend.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.research.paper.savedpaper.SavedPaper;

import java.util.Date;
import java.util.List;

public class Research {
    private String id;
    private String title;
    private Comment description;
    private Date startDate;
    private List<SavedPaper> savedPapers;
    private List<CachedPaper> cachedPapers;

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

    public Comment getDescription() {
        return description;
    }

    public void setDescription(Comment description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<SavedPaper> getSavedPapers() {
        return savedPapers;
    }

    public void setSavedPapers(List<SavedPaper> savedPapers) {
        this.savedPapers = savedPapers;
    }

    public List<CachedPaper> getCachedPapers() {
        return cachedPapers;
    }

    public void setCachedPapers(List<CachedPaper> cachedPapers) {
        this.cachedPapers = cachedPapers;
    }
}

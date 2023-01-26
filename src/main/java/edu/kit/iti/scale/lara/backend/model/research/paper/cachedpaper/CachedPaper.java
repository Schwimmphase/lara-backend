package edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper;

import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cached-papers", schema = "lara")
@IdClass(CachedPaper.CachedPaperId.class)
public class CachedPaper {
    @Id
    @ManyToOne
    private Paper paper;
    @Id
    @ManyToOne
    private Paper parentPaper;
    @Id
    @ManyToOne
    private Research research;
    private CachedPaperType type;

    public static class CachedPaperId implements Serializable {
        private Research research;
        private Paper paper;
    }

    public CachedPaper(Paper paper, Paper parentPaper, Research research, CachedPaperType type) {
        this.paper = paper;
        this.parentPaper = parentPaper;
        this.research = research;
        this.type = type;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public CachedPaperType getType() {
        return type;
    }

    public void setType(CachedPaperType type) {
        this.type = type;
    }

    public Paper getParentPaper() {
        return parentPaper;
    }

    public void setParentPaper(Paper parentPaper) {
        this.parentPaper = parentPaper;
    }
}

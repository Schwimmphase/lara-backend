package edu.kit.iti.scale.lara.backend.research.paper.cachedpaper;

import edu.kit.iti.scale.lara.backend.research.Research;
import edu.kit.iti.scale.lara.backend.research.paper.Paper;

public class CachedPaper {
    private Paper paper;
    private Research research;
    private CachedPaperType type;

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
}

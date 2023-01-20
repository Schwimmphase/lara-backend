package edu.kit.iti.scale.lara.backend.research.paper.savedpaper;

import edu.kit.iti.scale.lara.backend.research.Comment;
import edu.kit.iti.scale.lara.backend.research.Research;
import edu.kit.iti.scale.lara.backend.research.paper.Paper;

public class SavedPaper {
    private Paper paper;
    private Research research;
    private Comment comment;
    private Tag tag;
    private int relevance;
    private SaveState saveState;

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public SaveState getSaveState() {
        return saveState;
    }

    public void setSaveState(SaveState saveState) {
        this.saveState = saveState;
    }
}

package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "saved-papers", schema = "lara")
@IdClass(SavedPaper.SavedPaperId.class)
@NoArgsConstructor
public class SavedPaper {
    @Id
    @ManyToOne
    private Paper paper;
    @Id
    @ManyToOne
    private Research research;
    @OneToOne
    private Comment comment;
    @OneToMany
    private List<Tag> tag;
    private int relevance;
    private SaveState saveState;

    public static class SavedPaperId implements Serializable {
        private Research research;
        private Paper paper;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
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

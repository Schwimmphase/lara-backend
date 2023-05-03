package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A SavedPaper acts as a pointer to an actual Paper and also holds additional information about the paper
 *
 * @author ukgcc
 */
@Entity
@Table(name = "saved-papers")
@NoArgsConstructor
@Getter
@Setter
public class SavedPaper {
    @EmbeddedId
    @JsonUnwrapped
    private SavedPaperId savedPaperId;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonUnwrapped
    private Comment comment;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Tag> tags;
    private int relevance;
    private SaveState saveState;
    @Column(length = 1000)
    private String userPdfUrl;

    /**
     * The id of a SavedPaper is represented by the SavedPaperId Class. It contains the Paper the SavedPaper points to
     * and the Research the SavedPaper is saved to.
     *
     * @author unqkm
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    @Getter
    @Setter
    public static class SavedPaperId implements Serializable {
        @ManyToOne
        private Paper paper;
        @ManyToOne
        private Research research;
    }

    /**
     * Constructs a new SavedPaper
     *
     * @param paper     the Paper the savedPaper points to
     * @param research  the Research for which the SavedPaper was saved
     * @param comment   a Comment to make notes about the Paper
     * @param relevance indicates on a scale from 0-3 how relevant the Paper is for the Research
     * @param saveState the SaveState of the Paper
     */
    public SavedPaper(Paper paper, Research research, Comment comment, int relevance, SaveState saveState) {
        this.savedPaperId = new SavedPaperId(paper, research);
        this.comment = comment;
        this.tags = new ArrayList<>();
        this.relevance = relevance;
        this.saveState = saveState;
    }

    public Paper getPaper() {
        return savedPaperId.paper;
    }

    public Research getResearch() {
        return savedPaperId.research;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedPaper that = (SavedPaper) o;
        return Objects.equals(this.savedPaperId, that.savedPaperId);
    }

    @Override
    public int hashCode() {
        return savedPaperId != null ? savedPaperId.hashCode() : 0;
    }
}

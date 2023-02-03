package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;
    private int relevance;
    private SaveState saveState;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    @Getter
    @Setter
    public static class SavedPaperId implements Serializable {
        @ManyToOne
        @JsonUnwrapped
        private Paper paper;
        @ManyToOne
        @JsonUnwrapped
        @JsonIncludeProperties({"id"})
        private Research research;
    }

    public SavedPaper(Paper paper, Research research, Comment comment, int relevance, SaveState saveState) {
        this.savedPaperId = new SavedPaperId(paper, research);
        this.comment = comment;
        this.tags = new ArrayList<>();
        this.relevance = relevance;
        this.saveState = saveState;
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

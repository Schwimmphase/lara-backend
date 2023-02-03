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
@Table(name = "saved-papers", schema = "lara")
@IdClass(SavedPaper.SavedPaperId.class)
@NoArgsConstructor
@Getter
@Setter
public class SavedPaper {
    @Id
    @ManyToOne
    @JsonUnwrapped
    private Paper paper;
    @Id
    @ManyToOne
    @JsonUnwrapped
    @JsonIncludeProperties({ "id" })
    private Research research;
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
    public static class SavedPaperId implements Serializable {
        private Research research;
        private Paper paper;
    }

    public SavedPaper(Paper paper, Research research, Comment comment, int relevance, SaveState saveState) {
        this.paper = paper;
        this.research = research;
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
        if (!Objects.equals(paper, that.paper)) return false;
        return Objects.equals(research, that.research);
    }

    @Override
    public int hashCode() {
        int result = paper != null ? paper.hashCode() : 0;
        result = 31 * result + (research != null ? research.hashCode() : 0);
        return result;
    }
}

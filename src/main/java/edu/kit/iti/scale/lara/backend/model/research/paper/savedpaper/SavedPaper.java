package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
    @OneToOne
    @JsonUnwrapped
    private Comment comment;
    @OneToMany
    private List<Tag> tag;
    private int relevance;
    private SaveState saveState;

    public static class SavedPaperId implements Serializable {
        private Research research;
        private Paper paper;
    }
}

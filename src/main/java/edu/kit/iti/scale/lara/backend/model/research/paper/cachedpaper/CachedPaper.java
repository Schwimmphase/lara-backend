package edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "cached-papers", schema = "lara")
@IdClass(CachedPaper.CachedPaperId.class)
@NoArgsConstructor
@Getter
@Setter
public class CachedPaper {
    @Id
    @ManyToOne
    @JsonUnwrapped
    private Paper paper;
    @Id
    @ManyToOne
    @JsonIgnore
    private Research research;
    @JsonIgnore
    private CachedPaperType type;

    public static class CachedPaperId implements Serializable {
        private Research research;
        private Paper paper;
    }
}

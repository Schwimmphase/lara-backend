package edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "cached-papers")
@NoArgsConstructor
@Getter
@Setter
public class CachedPaper {

    @EmbeddedId
    @JsonUnwrapped
    private CachedPaperId cachedPaperId;
    private CachedPaperType type;

    public CachedPaper(Paper paper, Paper parentPaper, Research research, CachedPaperType type) {
        this.cachedPaperId = new CachedPaperId(paper, research, parentPaper);
        this.type = type;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    @Getter
    @Setter
    public static class CachedPaperId implements Serializable {
        @ManyToOne
        @JsonUnwrapped
        private Paper paper;
        @ManyToOne
        @JsonUnwrapped
        @JsonIncludeProperties({"id"})
        private Research research;
        @ManyToOne
        private Paper parentPaper;
    }
}

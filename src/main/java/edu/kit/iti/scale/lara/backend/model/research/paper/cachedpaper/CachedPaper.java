package edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
    private Paper parentPaper;
    @Id
    @ManyToOne
    @JsonIgnore
    private Research research;
    private CachedPaperType type;

    public CachedPaper(Paper paper, Paper parentPaper, Research research, CachedPaperType type) {
        this.paper = paper;
        this.parentPaper = parentPaper;
        this.research = research;
        this.type = type;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CachedPaperId implements Serializable {
        private Research research;
        private Paper paper;

    }
}

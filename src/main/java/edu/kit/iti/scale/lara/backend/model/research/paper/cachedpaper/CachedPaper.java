package edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CachedPaper acts as pointer to an actual Paper that appears as a citation or as a reference of a Paper
 * that has already benn saved to a Research
 *
 * @author ukgcc
 */
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

    /**
     * Constructs a new CachedPaper
     *
     * @param paper       the Paper the CachedPaper points to
     * @param parentPaper the Paper that has above Paper as a reference or citation
     * @param research    the Research the CachedPaper belongs to
     * @param type        the type of the CachedPaper
     */
    public CachedPaper(Paper paper, Paper parentPaper, Research research, CachedPaperType type) {
        this.cachedPaperId = new CachedPaperId(paper, research, parentPaper);
        this.type = type;
    }

    /**
     * The id of a CachedPaper is represented by the CachedPaperId Class. It contains the Paper the CachedPaper points to,
     * the Research the CachedPaper belongs to and the Paper that has the other Paper as a reference or citation.
     *
     * @author unqkm
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    @Getter
    @Setter
    public static class CachedPaperId implements Serializable {
        @ManyToOne(cascade = CascadeType.PERSIST)
        @JsonUnwrapped
        private Paper paper;
        @ManyToOne
        @JsonUnwrapped
        @JsonIncludeProperties({"id"})
        private Research research;
        @ManyToOne
        private Paper parentPaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CachedPaper)) return false;
        CachedPaper that = (CachedPaper) o;
        return Objects.equals(getCachedPaperId(), that.getCachedPaperId());
    }
}

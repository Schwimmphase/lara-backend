package edu.kit.iti.scale.lara.backend.model.research.paper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Paper that Users can save to their Researches
 *
 * @author ukgcc
 */
@Entity
@Table(name = "papers")
@NoArgsConstructor
@Getter
@Setter
public class Paper {
    @Id
    private String paperId;
    @Column(length = 1000)
    private String title;
    @JsonProperty("year")
    private int yearPublished;
    @JsonProperty("abstract")
    @Column(length = 10000)
    private String abstractText;
    private int citationCount;
    private int referenceCount;
    @Column(length = 1000)
    private String venue;
    @Column(length = 1000)
    private String pdfUrl;
    @OneToMany(mappedBy = "cachedPaperId.paper")
    @JsonIgnore
    private List<CachedPaper> cachedPapers;
    @OneToMany(mappedBy = "savedPaperId.paper")
    @JsonIgnore
    private List<SavedPaper> savedPapers;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Author> authors;

    /**
     * Constructs a new Paper
     *
     * @param paperId        the id of the Paper. It consists of a prefix that tells the origin of the paper, a separator
     *                       and the id of the original Paper from the origin.
     * @param title          the title of the paper
     * @param yearPublished  the year the paper was published
     * @param abstractText   the abstract of the paper
     * @param citationCount  indicates how often the paper was cited
     * @param referenceCount indicates how manx other papers this paper references
     * @param venue          the venue the paper was published in
     * @param pdfUrl         a link to a pdf document of the paper
     * @param authors        the authors of the paper
     */
    public Paper(String paperId, String title, int yearPublished, String abstractText, int citationCount, int referenceCount,
                 String venue, String pdfUrl, List<Author> authors) {
        this.paperId = paperId;
        this.title = title;
        this.yearPublished = yearPublished;
        this.abstractText = abstractText;
        this.citationCount = citationCount;
        this.referenceCount = referenceCount;
        this.venue = venue;
        this.pdfUrl = pdfUrl;
        this.cachedPapers = new ArrayList<>();
        this.savedPapers = new ArrayList<>();
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paper paper)) return false;
        return Objects.equals(getPaperId(), paper.getPaperId());
    }

    @Override
    public int hashCode() {
        return paperId != null ? paperId.hashCode() : 0;
    }
}

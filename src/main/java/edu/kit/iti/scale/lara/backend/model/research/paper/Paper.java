package edu.kit.iti.scale.lara.backend.model.research.paper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "papers", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class Paper {
    @Id
    private String paperId;
    private String title;
    @JsonProperty("year")
    private int yearPublished;
    @JsonProperty("abstract")
    private String abstractText;
    private int citationCount;
    private int referenceCount;
    private String venue;
    private String pdfUrl;
    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CachedPaper> cachedPapers;
    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SavedPaper> savedPapers;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Author> authors;

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

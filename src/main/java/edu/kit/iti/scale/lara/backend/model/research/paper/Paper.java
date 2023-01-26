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

@Entity
@Table(name = "papers", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class Paper {
    @Id
    private String paperId;
    private String title;
    private int year;
    @JsonProperty("abstract")
    private String abstractText;
    private int citationCount;
    private int referenceCount;
    private String venue;
    private String pdfUrl;
    @OneToMany(mappedBy = "paper")
    @JsonIgnore
    private List<CachedPaper> cachedPapers;
    @OneToMany(mappedBy = "paper")
    @JsonIgnore
    private List<SavedPaper> savedPapers;
    @ManyToMany
    private List<Author> author;

    public Paper(String paperId, String title, int year, String abstractText, int citationCount, int referenceCount,
                 String venue, String pdfUrl, List<Author> author) {
        this.paperId = paperId;
        this.title = title;
        this.year = year;
        this.abstractText = abstractText;
        this.citationCount = citationCount;
        this.referenceCount = referenceCount;
        this.venue = venue;
        this.pdfUrl = pdfUrl;
        this.cachedPapers = new ArrayList<>();
        this.savedPapers = new ArrayList<>();
        this.author = author;
    }
}

package edu.kit.iti.scale.lara.backend.model.research;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "researches")
@NoArgsConstructor
@Getter
@Setter
public class Research {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String title;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonUnwrapped
    private Comment description;
    private ZonedDateTime startDate;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "savedPaperId.research", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SavedPaper> savedPapers;
    @OneToMany(mappedBy = "cachedPaperId.research", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CachedPaper> cachedPapers;

    public Research(String title, Comment description, ZonedDateTime startDate, User user) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.user = user;
        this.savedPapers = new ArrayList<>();
        this.cachedPapers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Research research = (Research) o;
        return Objects.equals(id, research.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public boolean addSavedPaper(SavedPaper savedPaper) {
        return savedPapers.add(savedPaper);
    }

    public boolean addCachedPaper(CachedPaper cachedPaper) {
        return cachedPapers.add(cachedPaper);
    }

    public boolean removeSavedPaper(SavedPaper savedPaper) {
        return savedPapers.remove(savedPaper);
    }

}

package edu.kit.iti.scale.lara.backend.model.research;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
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


/**
 * Represents a Research that a User has started
 *
 * @author ukgcc
 * 
 */
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "savedPaperId.research", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<SavedPaper> savedPapers;
    @OneToMany(mappedBy = "cachedPaperId.research", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<CachedPaper> cachedPapers;
    @OneToMany(mappedBy = "research", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Tag> tags;

    /**
     * Constructs a new Research
     *
     * @param title       teh title of the Research
     * @param description a description to specify the Research
     * @param startDate   the date the User created the Research
     * @param user        the User that has created the Research
     */
    public Research(String title, Comment description, ZonedDateTime startDate, User user) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.user = user;
        this.savedPapers = new ArrayList<>();
        this.cachedPapers = new ArrayList<>();
        this.tags = new ArrayList<>();
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

    //Todo: clean up
    public boolean addSavedPaper(SavedPaper savedPaper) {
        return savedPapers.add(savedPaper);
    }

    //Todo: clean up
    public boolean addCachedPaper(CachedPaper cachedPaper) {
        return cachedPapers.add(cachedPaper);
    }

    //Todo: clean up
    public boolean removeSavedPaper(SavedPaper savedPaper) {
        return savedPapers.remove(savedPaper);
    }

    @Override
    public String toString() {
        return this.id + '$' + this.title;
    }


}

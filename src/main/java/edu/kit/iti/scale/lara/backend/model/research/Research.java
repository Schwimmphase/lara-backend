package edu.kit.iti.scale.lara.backend.model.research;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "researches", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class Research {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String title;
    @OneToOne
    @JsonUnwrapped
    private Comment description;
    private ZonedDateTime startDate;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "research")
    @JsonIgnore
    private List<SavedPaper> savedPapers;
    @OneToMany(mappedBy = "research")
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

    public boolean addSavedPaper(SavedPaper savedPaper) {
        return savedPapers.add(savedPaper);
    }

    public boolean addCachedPaper(CachedPaper cachedPaper) {
        return cachedPapers.add(cachedPaper);
    }

}

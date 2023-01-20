package edu.kit.iti.scale.lara.backend.model.research;

import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "researches", schema = "lara")
@NoArgsConstructor
public class Research {

    @Id
    private String id;
    private String title;
    @OneToOne
    private Comment description;
    private ZonedDateTime startDate;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "research")
    private List<SavedPaper> savedPapers;
    @OneToMany(mappedBy = "research")
    private List<CachedPaper> cachedPapers;

    public Research(String id, String title, Comment description, ZonedDateTime startDate, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.user = user;
        this.savedPapers = new ArrayList<>();
        this.cachedPapers = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Comment getDescription() {
        return description;
    }

    public void setDescription(Comment description) {
        this.description = description;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SavedPaper> getSavedPapers() {
        return savedPapers;
    }

    public void setSavedPapers(List<SavedPaper> savedPapers) {
        this.savedPapers = savedPapers;
    }

    public List<CachedPaper> getCachedPapers() {
        return cachedPapers;
    }

    public void setCachedPapers(List<CachedPaper> cachedPapers) {
        this.cachedPapers = cachedPapers;
    }
}

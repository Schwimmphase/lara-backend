package edu.kit.iti.scale.lara.backend.model.user;

import edu.kit.iti.scale.lara.backend.model.research.Research;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", schema = "lara")
public class User {
    private String username;
    @Id
    private String id;
    private String password;
    @ManyToOne
    private Research activeResearch;
    @ManyToOne
    private UserCategory userCategory;
    @OneToMany
    private List<Research> researches;

    public User(String username, String id, String password, UserCategory userCategory) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.userCategory = userCategory;
        this.researches = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Research getActiveResearch() {
        return activeResearch;
    }

    public void setActiveResearch(Research activeResearch) {
        this.activeResearch = activeResearch;
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    public List<Research> getResearches() {
        return researches;
    }

    public void setResearches(List<Research> researches) {
        this.researches = researches;
    }
}
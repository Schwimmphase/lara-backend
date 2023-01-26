package edu.kit.iti.scale.lara.backend.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String username;
    @Id
    @JsonProperty("userId")
    private String id;
    @JsonIgnore
    private String password;
    @ManyToOne
    @JsonIgnore
    private Research activeResearch;
    @ManyToOne
    private UserCategory userCategory;
    @OneToMany
    @JsonIgnore
    private List<Research> researches;

    public User(String username, String id, String password, UserCategory userCategory) {
        this.username = username;
        this.id = id;
        this.password = password;
        this.userCategory = userCategory;
        this.researches = new ArrayList<>();
    }
}
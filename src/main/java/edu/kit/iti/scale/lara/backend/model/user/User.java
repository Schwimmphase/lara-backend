package edu.kit.iti.scale.lara.backend.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String username;
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @JsonProperty("userId")
    private String id;
    @JsonIgnore
    private String password;
    @ManyToOne
    @JsonIgnore
    private Research activeResearch;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserCategory userCategory;

    public User(String username, String password, UserCategory userCategory) {
        this.username = username;
        this.password = password;
        this.userCategory = userCategory;
    }

    @Deprecated
    public boolean addResearch(Research research) {
        return true;
    }
}
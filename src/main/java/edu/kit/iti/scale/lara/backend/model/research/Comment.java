package edu.kit.iti.scale.lara.backend.model.research;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @JsonIgnore
    private String id;
    @JsonProperty("comment")
    private String text;

    public Comment(String id, String text) {
        this.id = id;
        this.text = text;
    }
}

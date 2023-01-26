package edu.kit.iti.scale.lara.backend.model.research.paper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class Author {
    @Id @JsonProperty("authorId")
    private String id;
    @JsonProperty("name")
    private String name;

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

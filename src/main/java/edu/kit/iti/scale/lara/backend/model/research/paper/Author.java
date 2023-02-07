package edu.kit.iti.scale.lara.backend.model.research.paper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Getter
@Setter
public class Author {
    @Id
    @JsonProperty(value = "authorId", access = JsonProperty.Access.WRITE_ONLY)
    private String id;
    @JsonProperty("name")
    private String name;

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return Objects.equals(getId(), author.getId());
    }

}

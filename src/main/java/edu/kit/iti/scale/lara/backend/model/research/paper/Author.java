package edu.kit.iti.scale.lara.backend.model.research.paper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents an Author of a Paper
 *
 * @author ukgcc
 * @version 1.0
 */
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

    /**
     * Constructs an Author
     *
     * @param id   the id of the Author
     * @param name the name of the Author
     */
    //Todo: generate id automatically
    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(getId(), author.getId());
    }

}

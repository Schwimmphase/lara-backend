package edu.kit.iti.scale.lara.backend.model.research.paper;

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
    @Id
    private String id;
    private String name;

    public Author(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

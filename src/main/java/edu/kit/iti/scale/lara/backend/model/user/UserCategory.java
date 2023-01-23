package edu.kit.iti.scale.lara.backend.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user-categories", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class UserCategory {
    @Id
    private String id;
    private String color;
    private String name;

    public UserCategory(String id, String color, String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }
}

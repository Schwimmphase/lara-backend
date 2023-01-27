package edu.kit.iti.scale.lara.backend.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user-categories", schema = "lara")
@NoArgsConstructor
@Getter
@Setter
public class UserCategory {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String color;
    private String name;

    public UserCategory(String color, String name) {
        this.color = color;
        this.name = name;
    }
}

package edu.kit.iti.scale.lara.backend.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Entity
@Table(name = "user-categories")
@NoArgsConstructor
@Getter
@Setter
public class UserCategory {

    public static final String ADMIN_CATEGORY = "ADMIN";

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCategory that)) return false;
        return Objects.equals(getId(), that.getId());
    }

}

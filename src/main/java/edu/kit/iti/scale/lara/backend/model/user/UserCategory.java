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

/**
 * Represents category that is used to further specify and categories a User
 *
 * @author ukgcc
 * 
 */
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

    /**
     * Constructor for a new UserCategory
     *
     * @param color a string representation of the hex-value for the color of the UserCategory
     * @param name  the name of the UserCategory. Each instance has to have a unique name.
     */
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

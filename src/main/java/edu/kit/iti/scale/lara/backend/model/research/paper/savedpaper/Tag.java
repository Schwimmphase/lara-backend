package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

/**
 * Represents a Tag that can be attached to SavedPapers of a Research to further specify and categorise them
 *
 * @author ukgcc
 */
@Entity
@Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String color;
    private String name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    private Research research;

    /**
     * Constructs a new Tag
     *
     * @param color    a string representation of the hex-value for the color of the Tag
     * @param name     the name of the Tag
     * @param research the research in whose context the Tag exists
     */
    public Tag(String color, String name, Research research) {
        this.color = color;
        this.name = name;
        this.research = research;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag tag)) return false;
        return Objects.equals(getId(), tag.getId());
    }

}

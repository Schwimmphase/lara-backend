package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags", schema = "lara")
@NoArgsConstructor
public class Tag {
    @Id
    private String id;
    private String color;
    private String name;
    @ManyToOne
    @JsonIgnore
    private Research research;

    public Tag(String id, String color, String name, Research research) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.research = research;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Research getResearch() {
        return research;
    }

    public void setResearch(Research research) {
        this.research = research;
    }
}

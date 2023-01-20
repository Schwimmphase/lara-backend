package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags", schema = "lara")
public class Tag {
    @Id
    private String id;
    private String color;
    private String name;

    public Tag(String id, String color, String name) {
        this.id = id;
        this.color = color;
        this.name = name;
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
}

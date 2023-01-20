package edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper;

import edu.kit.iti.scale.lara.backend.model.research.Research;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags", schema = "lara")
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    private String id;
    private String color;
    private String name;
    @ManyToOne
    private Research research;

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

package edu.kit.iti.scale.lara.backend.model.organizer;

import edu.kit.iti.scale.lara.backend.research.paper.Paper;

import java.util.List;

public abstract class Organizer {
    private String name;

    public abstract List<Paper> organize(List<Paper> papers);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package edu.kit.iti.scale.lara.backend.model.organizer;

import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

public interface Organizer {

    List<Paper> organize(List<Paper> papers);

}

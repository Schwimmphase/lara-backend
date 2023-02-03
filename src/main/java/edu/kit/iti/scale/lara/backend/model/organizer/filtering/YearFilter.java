package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

@NamedOrganizer("YearFiler")
public class YearFilter implements Filter {
    @Override
    public List<Paper> organize(List<Paper> papers) {
        //todo
        return null;
    }
}

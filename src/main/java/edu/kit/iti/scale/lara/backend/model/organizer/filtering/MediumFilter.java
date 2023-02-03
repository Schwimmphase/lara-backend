package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

@NamedOrganizer("MediumFilter")
public class MediumFilter<T extends Paper> implements Filter<T> {

    @Override
    public List<T> organize(List<T> papers) {
        //todo
        return null;
    }
}

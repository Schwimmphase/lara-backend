package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;
@NamedOrganizer("medium-filter")
public class MediumFilter<T extends Paper> implements Filter<T> {

    private final String medium;

    public MediumFilter(String argument) {
        if (argument == null || argument.isEmpty()) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        medium = argument;
    }

    @Override
    public List<T> organize(List<T> papers) {
        papers.removeIf(paper -> !paper.getVenue().equals(medium));
        return papers;
    }

}

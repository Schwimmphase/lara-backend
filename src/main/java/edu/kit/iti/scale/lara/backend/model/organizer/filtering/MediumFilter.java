package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@RequiredArgsConstructor
@Getter
@Setter
@NamedOrganizer("MediumFilter")
public class MediumFilter<T extends Paper> implements Filter<T> {

    private String medium;

    @Override
    public List<T> organize(List<T> papers) {
        papers.removeIf(paper -> !paper.getVenue().equals(medium));
        return papers;
    }

}

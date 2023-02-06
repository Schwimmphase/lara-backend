package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NamedOrganizer("YearFiler")
public class YearFilter<T extends Paper> implements Filter<T> {

    private int min;
    private int max;

    @Override
    public List<T> organize(List<T> papers) {
        papers.removeIf(paper -> paper.getYearPublished() < min || paper.getYearPublished() > max);
        return null;
    }
}

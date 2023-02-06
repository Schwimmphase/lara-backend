package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NamedOrganizer("YearSorter")
public class YearSorter<T extends Paper> extends Sorter<T> {

    public YearSorter(OrganizerRequest request) {
        super(request);
    }

    @Override
    public List<T> organize(List<T> papers) {
        papers.sort(Comparator.comparing(Paper::getYearPublished));
        if (getSortingDirection().equals(SortingDirection.DESCENDING)) {
            papers.sort(Collections.reverseOrder());
        }
        return papers;
    }
}

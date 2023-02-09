package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NamedOrganizer("year-sorter")
public class YearSorter<T extends Paper> extends Sorter<T> {

    public YearSorter(String argument) {
        super(argument);
    }

    @Override
    public List<T> organize(List<T> papers) {
        List<T> sortingPapers = new ArrayList<>(papers);
        if (getSortingDirection() == SortingDirection.DESCENDING) {
            sortingPapers.sort(Comparator.comparing(Paper::getYearPublished, Collections.reverseOrder()));
        } else {
            sortingPapers.sort(Comparator.comparing(Paper::getYearPublished));
        }
        return sortingPapers;
    }
}

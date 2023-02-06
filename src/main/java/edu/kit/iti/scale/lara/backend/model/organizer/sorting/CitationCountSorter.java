package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NamedOrganizer("citation-count-sorter")
public class CitationCountSorter<T extends Paper> extends Sorter<T> {

    public CitationCountSorter(String argument) {
        super(argument);
    }

    @Override
    public List<T> organize(List<T> papers) {
        papers = new ArrayList<>(papers);
        papers.sort(Comparator.comparing(Paper::getCitationCount));
        if (getSortingDirection().equals(SortingDirection.DESCENDING)) {
            papers.sort(Collections.reverseOrder());
        }
        return papers;
    }
}

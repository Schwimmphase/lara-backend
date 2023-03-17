package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This sorter sorts a list of papers by their citation count.
 * @param <T> the type paper
 *
 * @author unqkm
 */
@NamedOrganizer("citation-count-sorter")
public class CitationCountSorter<T extends Paper> extends Sorter<T> {

    public CitationCountSorter(String argument) {
        super(argument);
    }

    @Override
    public List<T> organize(List<T> papers) {
        List<T> sortingPapers = new ArrayList<>(papers);
        if (getSortingDirection() == SortingDirection.DESCENDING) {
            sortingPapers.sort(Comparator.comparing(Paper::getCitationCount, Collections.reverseOrder()));
        } else {
            sortingPapers.sort(Comparator.comparing(Paper::getCitationCount));
        }
        return sortingPapers;
    }
}

package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This sorter sorts a list of papers by their reference count.
 *
 * @param <T> the type paper
 * @author unqkm
 */
@NamedOrganizer("reference-count-sorter")
public class ReferenceCountSorter<T extends Paper> extends Sorter<T> {

    public ReferenceCountSorter(String argument) {
        super(argument);
    }

    @Override
    public List<T> organize(List<T> papers) {
        List<T> sortingPapers = new ArrayList<>(papers);
        if (getSortingDirection() == SortingDirection.DESCENDING) {
            sortingPapers.sort(Comparator.comparing(Paper::getReferenceCount, Collections.reverseOrder()));
        } else {
            sortingPapers.sort(Comparator.comparing(Paper::getReferenceCount));
        }
        return sortingPapers;
    }

}

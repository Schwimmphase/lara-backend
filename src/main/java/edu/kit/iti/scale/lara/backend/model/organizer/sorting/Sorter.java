package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;
import lombok.Getter;
import lombok.Setter;

/**
 * A sorter is a special organizer that can be used to sort a list of objects.
 * @param <T> the type of the objects that can be sorted
 *
 * @author ukcc, unqkm
 */
@Getter
@Setter
public abstract class Sorter<T> implements Organizer<T> {

    private SortingDirection sortingDirection;

    public Sorter(String argument) {
        sortingDirection = SortingDirection.getByApiName(argument).orElseThrow(IllegalArgumentException::new);
    }

}

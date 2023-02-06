package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Sorter<T> implements Organizer<T> {

    private SortingDirection sortingDirection;

    public Sorter(String argument) {
        sortingDirection = SortingDirection.getByApiName(argument).orElseThrow(IllegalArgumentException::new);
    }

}

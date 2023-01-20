package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;

public abstract class Sorter extends Organizer {

    private SortingDirection sortingDirection;

    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(SortingDirection sortingDirection) {
        this.sortingDirection = sortingDirection;
    }
}

package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;

import java.util.Arrays;

public abstract class Sorter<T> implements Organizer<T> {

    private SortingDirection sortingDirection;

    public Sorter(OrganizerRequest request) {
        if (Arrays.stream(SortingDirection.values())
                .anyMatch(sortingDirection -> sortingDirection.getApiName().equals(request.argument()))) {
            this.sortingDirection = sortingDirection.getByApiName(request.argument());
        } else {
            throw new IllegalArgumentException("Sorting direction not found");
        }
    }

    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(SortingDirection sortingDirection) {
        this.sortingDirection = sortingDirection;
    }
}

package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import java.util.Optional;

/**
 * This enum represents the sorting direction.
 *
 * @author ukgcc, unqkm
 */
public enum SortingDirection {
    ASCENDING("ascending"),
    DESCENDING("descending");

    private final String apiName;

    SortingDirection(String apiName) {
        this.apiName = apiName;
    }

    public String getApiName() {
        return apiName;
    }

    public static Optional<SortingDirection> getByApiName(String apiName) {
        for (SortingDirection sortingDirection : values()) {
            if (sortingDirection.getApiName().equals(apiName)) {
                return Optional.of(sortingDirection);
            }
        }
        return Optional.empty();
    }

}

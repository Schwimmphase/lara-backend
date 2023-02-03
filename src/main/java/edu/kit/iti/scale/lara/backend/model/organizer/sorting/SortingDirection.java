package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

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

    public SortingDirection getByApiName(String apiName) {
        for (SortingDirection sortingDirection : values()) {
            if (sortingDirection.getApiName().equals(apiName)) {
                return sortingDirection;
            }
        }
        throw new IllegalArgumentException("No sorting direction by this api name found");
    }
}

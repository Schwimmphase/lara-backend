package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NamedOrganizer("category-filter")
public class UserCategoryFilter<T extends User> implements Filter<T> {

    private final String[] userCategoryNames;

    public UserCategoryFilter(String ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Argument must not be null or empty");
        }
        userCategoryNames = ids.split(",");
    }

    @Override
    public List<T> organize(List<T> users) {
        List<T> organizedUsers = new ArrayList<>();
        for (T user : users) {
            if (Arrays.asList(userCategoryNames).contains(user.getUserCategory().getName())) {
                organizedUsers.add(user);
            }
        }
        return organizedUsers;
    }
}

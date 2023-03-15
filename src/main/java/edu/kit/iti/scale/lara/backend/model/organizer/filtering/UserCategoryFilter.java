package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.user.User;

import java.util.ArrayList;
import java.util.List;

@NamedOrganizer("userCategory-filter")
public class UserCategoryFilter<T extends User> implements Filter<T> {

    private final String[] userCategoryIds;

    public UserCategoryFilter(String ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Argument must not be null or empty");
        }
        userCategoryIds = ids.split(",");
    }

    @Override
    public List<T> organize(List<T> users) {

        List<T> organizedUsers = new ArrayList<>();
        for (String userCategoryId : userCategoryIds) {
            for (T user : users) {
                if (user.getUserCategory().getId() != null) {
                    if (user.getUserCategory().getId().equals(userCategoryId)) {
                        organizedUsers.add(user);
                    }
                }
            }
        }
        return organizedUsers;
    }
}

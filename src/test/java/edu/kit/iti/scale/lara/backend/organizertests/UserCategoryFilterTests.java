package edu.kit.iti.scale.lara.backend.organizertests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;
import edu.kit.iti.scale.lara.backend.model.organizer.filtering.UserCategoryFilter;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@InMemoryTest
public class UserCategoryFilterTests {

    @Autowired
    AuthorRepository authorRepository;

    private final Organizer<User> correctFilter = new UserCategoryFilter<>("student,prof");

    private final UserCategory student = new UserCategory("blue", "student");
    private final UserCategory prof = new UserCategory("blue", "prof");
    private final UserCategory tester = new UserCategory("blue", "tester");

    private final User user1 = new User("user1", "password", student);
    private final User user2 = new User("user2", "password", student);
    private final User user3 = new User("user3", "password", prof);
    private final User user4 = new User("user4", "password", prof);
    private final User user5 = new User("user5", "password", tester);

    private final List<User> users = List.of(user1, user2, user3, user4, user5);


    @Test
    public void testCorrectFilter() {
        List<User> filteredUsers = correctFilter.organize(users);

        for (User user : filteredUsers) {
            Assertions.assertThat(user.getUserCategory().getName().equals("student")
                    || user.getUserCategory().getName().equals("prof"));
        }
    }

    @Test
    public void testWrongSyntax() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Organizer<User> emptyFilter = new UserCategoryFilter<>("");
                });
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Organizer<User> nullFilter = new UserCategoryFilter<>(null);
                });
    }
}

package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@InMemoryTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    public void testCreateAndGetUser() {
        UserCategory userCategory = new UserCategory("test-category", "0000FF");
        User user = userService.createUser("test-user", "password", userCategory);

        try {
            Assertions.assertThat(userService.getUserById(user.getId())).isEqualTo(user);
        } catch (NotInDataBaseException e) {
            Assertions.fail("Not in database");
        }
    }

    @Test
    public void testCheckCredentials() {
        UserCategory userCategory = new UserCategory("test-category", "0000FF");
        User user = userService.createUser("test-user", "password", userCategory);

        Assertions.assertThat(userService.checkCredentials("password", user.getUsername())).isEqualTo(true);

        Assertions.assertThat(userService.checkCredentials("blabediblubla", user.getUsername())).isEqualTo(false);
    }
}

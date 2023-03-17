package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

        Assertions.assertThat(userService.checkCredentials("password", "wrongName")).isEqualTo(false);
    }

    @Test
    public void testUserOpenedResearch() {
        UserCategory userCategory = new UserCategory("test-category", "0000FF");
        User user = userService.createUser("test-user", "password", userCategory);

        Research research1 = new Research("title1", new Comment(), ZonedDateTime.now(), user);
        Research research2 = new Research("title2", new Comment(), ZonedDateTime.now(), user);

        userService.userOpenedResearch(user, research1);
        Assertions.assertThat(user.getActiveResearch().equals(research1));

        userService.userOpenedResearch(user, research2);
        Assertions.assertThat(user.getActiveResearch().equals(research2));
    }

    @Test
    public void testLoadByUserName() {
        UserCategory admin = new UserCategory("red", UserCategory.ADMIN_CATEGORY);
        User user = userService.createUser("test-user", "password", admin);

        Assertions.assertThat(userService.loadUserByUsername(user.getUsername())).isEqualTo(
                new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
                        Collections.singletonList((GrantedAuthority) () -> SecurityConfig.ADMIN_AUTHORITY)));

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("wrongName"));
    }

    @Test
    public void testUpdateUser() {
        UserCategory userCategory = new UserCategory("red", "test-category");
        UserCategory newUserCategory = new UserCategory("blue", "new-category");

        User user = userService.createUser("test-user", "password", userCategory);
        userService.updateUser(user, "newName", "newPassword", newUserCategory);

        Assertions.assertThat(user.getUsername().equals("newName"));
        Assertions.assertThat(user.getPassword().equals("newPassword"));
        Assertions.assertThat(user.getUserCategory().equals(newUserCategory));

    }
}

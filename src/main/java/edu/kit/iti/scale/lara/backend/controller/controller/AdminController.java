package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.CategoryRequest;
import edu.kit.iti.scale.lara.backend.controller.request.UserRequest;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminController {

    public ResponseEntity<List<User>> listUsers() {

        //mock
        UserCategory testUser = new UserCategory("#0000FF", "Test-User");
        List<User> users = new ArrayList<>();
        User user1 = new User("one", "password1", testUser);
        User user2 = new User("two", "password2", testUser);
        User user3 = new User("three", "password3", testUser);
        User user4 = new User("four", "password4", testUser);
        User user5 = new User("five", "password5", testUser);
        User user6 = new User("six", "password6", testUser);
        User user7 = new User("seven", "password7", testUser);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> createUser(UserRequest request, User admin) {

        //mock
        UserCategory testUser = new UserCategory("#0000FF", "Test-User");
        User user = new User("createdUser", "password", testUser);

        return ResponseEntity.ok(user);
    }

    public HttpStatus deleteUser(String userId, User admin) {

        //mock
        return HttpStatus.OK;
    }

    public ResponseEntity<User> updateUser(String userId, UserRequest request, User admin) {

        //mock
        UserCategory testUser = new UserCategory("#0000FF", "Test-User");
        User user = new User("updatedUser", "password", testUser);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<UserCategory> createCategory(CategoryRequest request, User admin) {

        //mock
        UserCategory newUserCategory = new UserCategory("#0000FF", "New-User-Category");

        return ResponseEntity.ok(newUserCategory);
    }

    public ResponseEntity<UserCategory> updateCategory(String id, CategoryRequest request, User admin) {

        //mock
        UserCategory updatedUserCategory = new UserCategory("#0000FF", "Updated-User-Category");

        return ResponseEntity.ok(updatedUserCategory);
    }

    public HttpStatus deleteCategory(String id, User admin) {

        //mock
        return HttpStatus.OK;
    }
}

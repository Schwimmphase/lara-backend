package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.CategoryRequest;
import edu.kit.iti.scale.lara.backend.controller.request.UserRequest;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usermanagement")
public class AdminController {

    @GetMapping("/")
    public ResponseEntity<List<User>> listUsers() {

        //mock
        UserCategory testUser = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        List<User> users = new ArrayList<>();
        User user1 = new User("one","11111", "password1", testUser);
        User user2 = new User("two","22222", "password2", testUser);
        User user3 = new User("three","33333", "password3", testUser);
        User user4 = new User("four","44444", "password4", testUser);
        User user5 = new User("five","55555", "password5", testUser);
        User user6 = new User("six","66666", "password6", testUser);
        User user7 = new User("seven","77777", "password7", testUser);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);

        return ResponseEntity.ok(users);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserRequest request, User admin) {

        //mock
        UserCategory testUser = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user = new User("createdUser","12345", "password", testUser);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public HttpStatus deleteUser(@PathVariable String userId, User admin) {

        //mock
        return HttpStatus.OK;
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody UserRequest request, User admin) {

        //mock
        UserCategory testUser = new UserCategory("aaaaa" ,"#0000FF", "Test-User");
        User user = new User("updatedUser","12345", "password", testUser);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/category")
    public ResponseEntity<UserCategory> createCategory(CategoryRequest request, User admin) {

        //mock
        UserCategory newUserCategory = new UserCategory("bbbbb" ,"#0000FF", "New-User-Category");

        return ResponseEntity.ok(newUserCategory);
    }

    @GetMapping("/category")
    public ResponseEntity<List<UserCategory>> listCategories(User admin) {

        //mock
        List<UserCategory> categories = new ArrayList<>();

        categories.add( new UserCategory("aaaaa" ,"#0000FF", "Test-User"));
        categories.add(new UserCategory("bbbbb" ,"#0000FF", "New-User-Category"));

        return ResponseEntity.ok(categories);
    }

    @PostMapping("/category/{id}")
    public ResponseEntity<UserCategory> updateCategory(@PathVariable String id, @RequestBody CategoryRequest request,
                                                       User admin) {

        //mock
        UserCategory updatedUserCategory = new UserCategory("ccccc" ,"#0000FF", "Updated-User-Category");

        return ResponseEntity.ok(updatedUserCategory);
    }

    @DeleteMapping("/category/{id}")
    public HttpStatus deleteCategory(@PathVariable String id, User admin) {

        //mock
        return HttpStatus.OK;
    }
}

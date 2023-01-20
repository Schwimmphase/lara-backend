package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.CategoryRequest;
import edu.kit.iti.scale.lara.backend.controller.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
public class AdminController {

    public ResponseEntity<List<User>> listUsers() {

        // TODO

        return null;
    }

    public ResponseEntity<User> createUser(UserRequest request, User admin) {

        // TODO

        return null;
    }

    public HttpStatus deleteUser(String userId, User admin) {

        // TODO

        return null;
    }

    public ResponseEntity<User> updateUser(String userId, UserRequest request, User admin) {

        // TODO

        return null;
    }

    public ResponseEntity<Locale.Category> createCategory(CategoryRequest request, User admin) {

        // TODO

        return null;
    }

    public ResponseEntity<Locale.Category> updateCategory(String id, CategoryRequest request, User admin) {

        // TODO

        return null;
    }

    public HttpStatus deleteCategory(String id, User admin) {

        // TODO

        return null;
    }
}

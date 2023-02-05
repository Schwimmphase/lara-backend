package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.controller.request.CategoryRequest;
import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.controller.request.UserRequest;
import edu.kit.iti.scale.lara.backend.controller.service.UserCategoryService;
import edu.kit.iti.scale.lara.backend.controller.service.UserService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.organizer.OrganizerList;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usermanagement")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserCategoryService userCategoryService;

    @GetMapping("/")
    public ResponseEntity<Map<String, List<User>>> listUsers(
            @RequestBody @NotNull Map<String, List<OrganizerRequest>> request) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());
        OrganizerList<User> organizerList = OrganizerList.createFromOrganizerRequests(organizers);

        List<User> users = userService.getUsers();

        users = organizerList.organize(users);
        return ResponseEntity.ok(Map.of("users", users));
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody @NotNull UserRequest request) {
        if (request.password() == null || request.password().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must not be null or empty");
        }

        if (request.username().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be empty");
        }

        try {
            UserCategory category = userCategoryService.getUserCategoryByName(request.userCategory());
            User user = userService.createUser(request.username(), request.password(), category);
            return ResponseEntity.ok(user);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this name not found");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NotNull String userId) {
        try {
            User user = userService.getUserById(userId);
            userService.deleteUser(user);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this id not found");
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable @NotNull String userId,
                                           @RequestBody @NotNull UserRequest request) {
        if (request.username().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be empty");
        }

        try {
            UserCategory category = userCategoryService.getUserCategoryByName(request.userCategory());
            User user = userService.getUserById(userId);
            user = userService.updateUser(user, request.username(), request.password(), category);
            return ResponseEntity.ok(user);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User category with this name or user with this id not found");
        }
    }

    @PostMapping("/category")
    public ResponseEntity<UserCategory> createCategory(@RequestBody @NotNull CategoryRequest request) {
        try {
            UserCategory userCategory = userCategoryService.createCategory(request.name(), request.color());
            return ResponseEntity.ok(userCategory);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String, List<UserCategory>>> listCategories() {
        List<UserCategory> userCategories = userCategoryService.getUserCategories();
        return ResponseEntity.ok(Map.of("categories", userCategories));
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<UserCategory> updateCategory(@PathVariable @NotNull String id,
                                                       @RequestBody @NotNull CategoryRequest request) {
        if (request.name().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name must not be empty");
        }

        if (request.color().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Color must not be empty");
        }

        try {
            UserCategory userCategory = userCategoryService.getUserCategoryByName(id);
            userCategory = userCategoryService.updateCategory(userCategory, request.name(), request.color());
            return ResponseEntity.ok(userCategory);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this name not found");
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @NotNull String id) {
        try {
            UserCategory userCategory = userCategoryService.getUserCategoryByName(id);
            userCategoryService.deleteCategory(userCategory);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this name not found");
        }
    }
}

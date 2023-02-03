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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestParam @NotNull Map<String, List<OrganizerRequest>> request) {
        List<OrganizerRequest> organizers = request.getOrDefault("organizers", List.of());
        OrganizerList<User> organizerList = OrganizerList.createFromOrganizerRequests(organizers);

        List<User> users = userService.getUsers();

        users = organizerList.organize(users);
        return ResponseEntity.ok(Map.of("users", users));
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody @NotNull UserRequest request, @RequestAttribute("user") User admin) {
        if (request.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must not be null");
        }

        try {
            UserCategory category = userCategoryService.getUserCategory(request.userCategory());
            User user = userService.createUser(request.username(), request.password(), category);
            return ResponseEntity.ok(user);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this id not found");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NotNull String userId, User admin) {
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
                                           @RequestBody @NotNull UserRequest request,
                                           @RequestAttribute("user") User admin) {
        try {
            UserCategory category = userCategoryService.getUserCategory(request.userCategory());
            User user = userService.getUserById(userId);
            userService.updateUser(user, request.username(), request.password(), category);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category or user with this id not found");
        }
    }

    @PostMapping("/category")
    public ResponseEntity<UserCategory> createCategory(@RequestBody @NotNull CategoryRequest request,
                                                       @RequestAttribute("user") User admin) {
        try {
            UserCategory userCategory = userCategoryService.createCategory(request.name(), request.color());
            return ResponseEntity.ok(userCategory);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String, List<UserCategory>>> listCategories(@RequestAttribute("user") User admin) {
        List<UserCategory> userCategories = userCategoryService.getUserCategories();
        return ResponseEntity.ok(Map.of("categories", userCategories));
    }

    @PostMapping("/category/{id}")
    public ResponseEntity<UserCategory> updateCategory(@PathVariable @NotNull String id,
                                                       @RequestBody @NotNull CategoryRequest request,
                                                       @RequestAttribute("user") User admin) {
        try {
            UserCategory userCategory = userCategoryService.getUserCategory(id);
            userCategoryService.updateCategory(userCategory, request.name(), request.color());
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this id not found");
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @NotNull String id,
                                               @RequestAttribute("user") User admin) {
        try {
            UserCategory userCategory = userCategoryService.getUserCategory(id);
            userCategoryService.deleteCategory(userCategory);
            return ResponseEntity.ok().build();
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this id not found");
        }
    }
}

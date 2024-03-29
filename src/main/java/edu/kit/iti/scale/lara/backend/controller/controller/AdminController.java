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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

/**
 * This class contains all the rest api endpoints for controlling users and their categories.
 *
 * @author unqkm
 */
@RestController
@RequestMapping("/usermanagement")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserCategoryService userCategoryService;

    /**
     * Get all users.
     *
     * @param organizers a list of organizers to organize the users.
     * @return           a list of users.
     */
    @GetMapping("")
    public ResponseEntity<Map<String, List<User>>> listUsers(
            @RequestParam(value = "organizer", required = false) List<OrganizerRequest> organizers) {
        if (organizers == null) {
            return ResponseEntity.ok(Map.of("users", userService.getUsers()));
        }

        try {
            OrganizerList<User> organizerList = OrganizerList.createFromOrganizerRequests(organizers);

            List<User> users = userService.getUsers();

            users = organizerList.organize(users);
            return ResponseEntity.ok(Map.of("users", users));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Create a new user.
     *
     * @param request the request body containing the username, password and user category.
     * @return        the created user and bad request if the user category was not found.
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody @NotNull UserRequest request) {
        if (request.password() == null || request.password().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must not be null or empty");
        }

        if (request.username().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be empty");
        }

        try {
            UserCategory category = userCategoryService.getUserCategoryByName(request.usercategory());
            User user = userService.createUser(request.username(), request.password(), category);
            return ResponseEntity.ok(user);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this name not found");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Delete a user.
     *
     * @param userId the id of the user to delete.
     * @return       a response entity with status code 200 and bad request if the user was not found.
     */
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

    /**
     * Update a user.
     *
     * @param userId  the id of the user to update.
     * @param request the request body containing the new username, password and user category.
     * @return        the updated user and bad request if the user category was not found.
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable @NotNull String userId,
                                           @RequestBody @NotNull UserRequest request) {
        if (request.username().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must not be empty");
        }

        try {
            UserCategory category = userCategoryService.getUserCategoryByName(request.usercategory());
            User user = userService.getUserById(userId);
            user = userService.updateUser(user, request.username(), request.password(), category);
            return ResponseEntity.ok(user);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User category with this name or user with this id not found");
        }
    }

    /**
     * Create a new user category.
     *
     * @param request the request body containing the name and color of the user category.
     * @return        the created user category and bad request if a user category with the same name already exists.
     */
    @PostMapping("/category")
    public ResponseEntity<UserCategory> createCategory(@RequestBody @NotNull CategoryRequest request) {
        try {
            UserCategory userCategory = userCategoryService.createCategory(request.name(), request.color());
            return ResponseEntity.ok(userCategory);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * List all user categories.
     *
     * @return a list of user categories.
     */
    @GetMapping("/category")
    public ResponseEntity<Map<String, List<UserCategory>>> listCategories() {
        List<UserCategory> userCategories = userCategoryService.getUserCategories();
        return ResponseEntity.ok(Map.of("categories", userCategories));
    }

    /**
     * Update a user category.
     *
     * @param id        the id of the user category to update.
     * @param request   the request body containing the new name and color of the user category.
     * @return          the updated user category and bad request if the user category was not found, name or color
     *                  is empty or a user category with the id not found.
     */
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
            UserCategory userCategory = userCategoryService.getUserCategory(id);
            userCategory = userCategoryService.updateCategory(userCategory, request.name(), request.color());
            return ResponseEntity.ok(userCategory);
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this id not found");
        }
    }

    /**
     * Delete a user category.
     *
     * @param id the id of the user category to delete.
     * @return   an empty response with status code 200 and bad request if the user category was not found or a user
     *           has this category.
     */
    @DeleteMapping("/category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @NotNull String id) {
        try {
            UserCategory userCategory = userCategoryService.getUserCategory(id);
            if (userService.getUsersByUserCategory(userCategory).isEmpty()) {
                userCategoryService.deleteCategory(userCategory);
                return ResponseEntity.ok().build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category cannot be deleted because a User has this category");
            }
        } catch (NotInDataBaseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User category with this name not found");
        }
    }
}

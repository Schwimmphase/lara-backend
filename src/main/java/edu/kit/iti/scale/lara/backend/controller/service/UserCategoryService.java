package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used to handle everything about userCategories
 *
 * @author ukgcc
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    /**
     * If there isn't already a user-category with this name it creates a new user-category and saves it into the UserCategoryRepository.
     *
     * @param name  the name of the user-category
     * @param color the color of the user-category in hex as a String
     * @return the created user-category
     */
    public UserCategory createCategory(String name, String color) {
        if (!userCategoryRepository.existsByName(name)) {
            UserCategory userCategory = new UserCategory(color, name);
            userCategoryRepository.save(userCategory);
            return userCategory;
        } else {
            throw new IllegalArgumentException("User category with this name already exists");
        }
    }

    /**
     * Finds the user-category with the given id from the UserCategoryRepository
     *
     * @param id the id of the user-category
     * @return the category with this id
     * @throws NotInDataBaseException when there is no user-category with this id
     */
    public UserCategory getUserCategory(String id) throws NotInDataBaseException {
        if (userCategoryRepository.findById(id).isPresent()) {
            return userCategoryRepository.findById(id).get();
        } else {
            throw new NotInDataBaseException();
        }
    }

    /**
     * Finds the user-category with the given name from the UserCategoryRepository
     *
     * @param name the name of the user-category
     * @return the user-category with this name
     * @throws NotInDataBaseException when there is no user-category with this name
     */
    public UserCategory getUserCategoryByName(String name) throws NotInDataBaseException {
        if (!userCategoryRepository.findByName(name).isEmpty()) {
            return userCategoryRepository.findByName(name).get(0);
        } else {
            throw new NotInDataBaseException();
        }
    }

    /**
     * Updates a user-category by assigning a new name and a new color
     *
     * @param userCategory the user-category to be updated
     * @param newName      the new name
     * @param newColor     the new color
     * @return the updated user-category
     */
    public UserCategory updateCategory(UserCategory userCategory, String newName, String newColor) {
        userCategory.setName(newName);
        userCategory.setColor(newColor);
        userCategoryRepository.save(userCategory);
        return userCategory;
    }

    /**
     * Deletes a user-category from the USerCategoryRepository
     *
     * @param userCategory the user-category to be deleted
     */
    public void deleteCategory(UserCategory userCategory) {
        userCategoryRepository.delete(userCategory);
    }

    /**
     * Finds all user-categories that are saved in the UserCategoryRepository
     *
     * @return all user-categories
     */
    public List<UserCategory> getUserCategories() {
        return userCategoryRepository.findAll();
    }
}

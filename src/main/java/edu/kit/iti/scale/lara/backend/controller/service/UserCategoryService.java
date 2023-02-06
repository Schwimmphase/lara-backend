package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.stereotype.Service;


@Service
public class UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    public UserCategoryService(UserCategoryRepository userCategoryRepository) {
        this.userCategoryRepository = userCategoryRepository;
    }

    public UserCategory createCategory(String name, String color) {
        if (!userCategoryRepository.existsByName(name)) {
            UserCategory userCategory = new UserCategory(color, name);
            userCategoryRepository.save(userCategory);
            return userCategory;
        } else {
            throw new IllegalArgumentException("User category with this name already exists");
        }
    }

    public UserCategory getUserCategory(String id) throws NotInDataBaseException {
        if(userCategoryRepository.findById(id).isPresent()) {
            return userCategoryRepository.findById(id).get();
        } else {
            throw new NotInDataBaseException();
        }
    }

    public void updateCategory(UserCategory userCategory, String newName, String newColor) {
        userCategory.setName(newName);
        userCategory.setColor(newColor);
    }

    public void deleteCategory(UserCategory userCategory) {
        userCategoryRepository.delete(userCategory);
    }
}

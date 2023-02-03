package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.service.UserCategoryService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@InMemoryTest
public class UserCategoryServiceTests {

    @Autowired
    private UserCategoryService userCategoryService;

    @Test
    public void testCreateAndGetUserCategory() {
        try {
            UserCategory userCategory = userCategoryService.createCategory("Test-Category", "0000FF");
            Assertions.assertThat(userCategoryService.getUserCategory(userCategory.getId())).isEqualTo(userCategory);
        } catch (NotInDataBaseException e) {
            Assertions.fail("UserCategory not in Database");
        }
    }

    @Test
    public void testUpdateUserCategory() {
        UserCategory userCategory = userCategoryService.createCategory("Test-Category", "0000FF");

        userCategoryService.updateCategory(userCategory, "new-name", "FF0000");

        Assertions.assertThat(userCategory.getName()).isEqualTo("new-name");
        Assertions.assertThat(userCategory.getColor()).isEqualTo("FF0000");
    }

    @Test
    public void testDeleteUserCategory() {
        UserCategory userCategory = userCategoryService.createCategory("Test-Category", "0000FF");

        userCategoryService.deleteCategory(userCategory);

        boolean exceptionThrown = false;
        try {
            userCategoryService.getUserCategory(userCategory.getId());
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }
}


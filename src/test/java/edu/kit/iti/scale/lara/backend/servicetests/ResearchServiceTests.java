package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.config.RsaKeyProperties;
import edu.kit.iti.scale.lara.backend.controller.config.SecurityConfig;
import edu.kit.iti.scale.lara.backend.controller.config.WebConfig;
import edu.kit.iti.scale.lara.backend.controller.controller.PaperController;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.controller.service.RecommendationService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.kit.iti.scale.lara.backend.TestObjects.paper;
import static edu.kit.iti.scale.lara.backend.TestObjects.savedPaper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@InMemoryTest
public class ResearchServiceTests {
    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResearchService researchService;

    @Test
    public void testCreateAndGetResearch() {
        User user = createUser();

        Research research = researchService.createResearch(user, "test-research", "test-description");

        try {
            Assertions.assertThat(researchService.getResearch(research.getId(), user)).isEqualTo(research);
        } catch (WrongUserException e) {
            Assertions.fail("Wrong User");
        } catch (NotInDataBaseException e) {
            Assertions.fail("Not in database");
        }
    }

    @Test
    public void testGetResearches() {
        User user = createUser();

        Research research1 = researchService.createResearch(user, "research1", "1");
        Research research2 = researchService.createResearch(user, "research2", "2");

        Assertions.assertThat(researchService.getResearches(user).stream().toList()).isEqualTo(List.of(research1, research2));
    }

    @Test
    public void testDeleteResearch() {
        User user = createUser();

        Research research = researchService.createResearch(user, "test-research", "test-description");
        user.setActiveResearch(research);

        try {
            Assertions.assertThat(researchService.getResearch(research.getId(), user)).isEqualTo(research);
        } catch (WrongUserException e) {
            Assertions.fail("Wrong User");
        } catch (NotInDataBaseException e) {
            Assertions.fail("Not in database");
        }

        researchService.deleteResearch(research, user);
        Assertions.assertThat(user.getActiveResearch() == null);

        boolean exceptionThrown = false;
        try {
            researchService.getResearch(research.getId(), user);
        } catch (WrongUserException e) {
            Assertions.fail("Wrong User");
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }

    @Test
    public void testGetRecommendations() throws IOException {

        User user = createUser();
        Research research = researchService.createResearch(user, "test-research", "test-description");

        Assertions.assertThat(researchService.getRecommendations(research)).isEqualTo(List.of());
    }

    @Test
    public void testUpdateResearch() {
        User user = createUser();

        Research research = researchService.createResearch(user, "test-research", "test-description");

        researchService.updateResearch(research, "new-title", "new-description");

        Assertions.assertThat(research.getTitle()).isEqualTo("new-title");
        Assertions.assertThat(research.getDescription().getText()).isEqualTo("new-description");
    }


    private User createUser() {
        UserCategory userCategory = new UserCategory("test-category", "0000FF");
        userCategoryRepository.save(userCategory);

        Assertions.assertThat(userCategoryRepository.findById(userCategory.getId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(userCategoryRepository.findById(userCategory.getId()).get()).isEqualTo(userCategory);

        User user = new User("test-user", "password", userCategory);
        userRepository.save(user);

        Assertions.assertThat(userRepository.findById(user.getId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(userRepository.findById(user.getId()).get()).isEqualTo(user);

        return user;
    }
}

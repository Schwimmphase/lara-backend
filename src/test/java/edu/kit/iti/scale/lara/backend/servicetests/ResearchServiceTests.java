package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.controller.service.CacheService;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import java.util.List;

import static edu.kit.iti.scale.lara.backend.TestObjects.*;

@InMemoryTest
public class ResearchServiceTests {
    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResearchService researchService;

    @Autowired
    private PaperService paperService;

    @Autowired
    CacheService cacheService;

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
    public void testGetReferences() {
        User user = createUser();
        Research research = researchService.createResearch(user, "test-research", "test-description");

        Paper paper1 = new Paper("id1", "paper1", 2023, "abstract1",
                0, 0, "venue1", "url1", List.of());
        paperService.savePaperToDataBase(paper1);

        Paper paper2 = new Paper("id2", "paper2", 2023, "abstract2",
                0, 0, "venue2", "url2", List.of());
        paperService.savePaperToDataBase(paper2);

        SavedPaper savedPaper1 = null;
        SavedPaper savedPaper2 = null;
        CachedPaper cachedPaper1 = null;
        CachedPaper cachedPaper2 = null;
        try {
            savedPaper1 = paperService.createSavedPaper(research, paper1, SaveState.ADDED);
            savedPaper2 = paperService.createSavedPaper(research, paper2, SaveState.HIDDEN);
            cachedPaper1 = cacheService.createCachedPaper(research, paper1, paper2, CachedPaperType.REFERENCE);
            cachedPaper2 = cacheService.createCachedPaper(research, paper2, paper1, CachedPaperType.CITATION);
        } catch (IOException e) {
            Assertions.fail(e.getMessage(), e);
        }

        Assertions.assertThat(researchService.getReferences(research, List.of(paper1, paper2))).isEqualTo(List.of(cachedPaper1));
        Assertions.assertThat(researchService.getReferences(research, List.of(paper1))).isEqualTo(List.of());
    }

    @Test
    public void testGetCitations() {
        User user = createUser();
        Research research = researchService.createResearch(user, "test-research", "test-description");

        Paper paper1 = new Paper("id1", "paper1", 2023, "abstract1",
                0, 0, "venue1", "url1", List.of());
        paperService.savePaperToDataBase(paper1);

        Paper paper2 = new Paper("id2", "paper2", 2023, "abstract2",
                0, 0, "venue2", "url2", List.of());
        paperService.savePaperToDataBase(paper2);

        SavedPaper savedPaper1 = null;
        SavedPaper savedPaper2 = null;
        CachedPaper cachedPaper1 = null;
        CachedPaper cachedPaper2 = null;
        try {
            savedPaper1 = paperService.createSavedPaper(research, paper1, SaveState.ADDED);
            savedPaper2 = paperService.createSavedPaper(research, paper2, SaveState.HIDDEN);
            cachedPaper1 = cacheService.createCachedPaper(research, paper1, paper2, CachedPaperType.REFERENCE);
            cachedPaper2 = cacheService.createCachedPaper(research, paper2, paper1, CachedPaperType.CITATION);
        } catch (IOException e) {
            Assertions.fail(e.getMessage(), e);
        }

        Assertions.assertThat(researchService.getCitations(research, List.of(paper1, paper2))).isEqualTo(List.of());

        paperService.changeSaveState(savedPaper2, SaveState.ADDED);

        Assertions.assertThat(researchService.getCitations(research, List.of(paper1, paper2))).isEqualTo(List.of(cachedPaper2));
    }

    @Test
    public void testSearchByKeyWord() {
        User user = createUser();
        Research research = researchService.createResearch(user, "test-research", "test-description");

        try {
            List<Paper> papers = researchService.searchByKeyword("graph", research);

        } catch (IOException e) {
            Assertions.fail("");
        }
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

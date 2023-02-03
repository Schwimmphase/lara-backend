package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.ResearchRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.controller.service.CacheService;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
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

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

public class CacheServiceTests {

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResearchRepository researchRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PaperService paperService;

    @Autowired
    CacheService cacheService;

    @Autowired

    @Test
    public void testInitializeCache() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper1 = new Paper("id1", "paper1", 2023, "abstract1",
                0, 0, "venue1", "url1", List.of(author));
        paperService.savePaperToDataBase(paper1);

        Paper paper2 = new Paper("id2", "paper2", 2023, "abstract2",
                0, 0, "venue2", "url2", List.of(author));
        paperService.savePaperToDataBase(paper2);

        SavedPaper savedPaper1 = paperService.createSavedPaper(research, paper1, SaveState.ADDED);
        SavedPaper savedPaper2 = paperService.createSavedPaper(research, paper2, SaveState.ADDED);

        try {
            cacheService.initializeCache(List.of(savedPaper1, savedPaper2), research);
        } catch (IOException e) {
            Assertions.fail("SemanticScholarApi didn´t work");
        }


    }

    @Test
    public void testFlushCacheResearch() {

    }

    @Test
    public void testCreateCachedPaper() {

    }

    @Test
    public void testRemovePaper() {

    }

    @Test
    public void testGetReferences() {

    }

    @Test
    public void testGetCitations() {

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

    private Research createResearch(User user) {
        Research research = new Research("test-research", new Comment("test-comment"), ZonedDateTime.now(), user);
        researchRepository.save(research);
        user.addResearch(research);
        userRepository.save(user);

        Assertions.assertThat(researchRepository.findById(research.getId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(researchRepository.findById(research.getId()).get()).isEqualTo(research);

        return research;
    }

    private Author createAuthor() {
        Author author = new Author("testId", "test-author");
        authorRepository.save(author);

        Assertions.assertThat(authorRepository.findById(author.getId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(authorRepository.findById(author.getId()).get()).isEqualTo(author);
        return author;
    }
}

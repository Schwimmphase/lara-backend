package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.*;
import edu.kit.iti.scale.lara.backend.controller.service.CacheService;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.controller.service.ResearchService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@InMemoryTest
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
    ResearchService researchService;

    @Autowired
    CacheService cacheService;
    @Autowired
    PaperRepository paperRepository;
    @Autowired
    CachedPaperRepository cachedPaperRepository;


    @Test
    public void testInitializeCache() {
        User user = createUser();
        Research research = researchService.createResearch(user, "test-research", "test-description");
        try {

            Paper paper = researchService.searchByKeyword("Graph", research).get(0);

            paperService.getPaper(paper.getPaperId(), true);
            paperService.createSavedPaper(research, paper, SaveState.ADDED);

            cacheService.initializeCache(research);

            List<CachedPaper> cache = cachedPaperRepository.findByCachedPaperIdResearch(research);

            for (CachedPaper cachedPaper : cache) {
                Assertions.assertThat(cachedPaper.getCachedPaperId().getParentPaper()).isEqualTo(paper);
            }


        } catch (IOException | NotInDataBaseException e) {
            Assertions.fail(e.getMessage(), e);
        }

    }

    @Test
    public void testDeleteCachedPaper() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id1", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        Paper parentPaper = new Paper("id2", "parentPaper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(parentPaper);

        CachedPaper cachedPaper = cacheService.createCachedPaper(research, paper, parentPaper, CachedPaperType.CITATION);
        try {
            cacheService.deleteCachedPaper(paper, research);
        } catch (NotInDataBaseException e) {
            Assertions.fail(e.getMessage());
        }

        Assertions.assertThat(paperRepository.findById("id1").isPresent()).isEqualTo(false);
        Assertions.assertThat(paperRepository.findById("id2").isPresent()).isEqualTo(false);

    }

    @Test
    public void testGetCitations() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id1", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        Paper parentPaper = new Paper("id2", "parentPaper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(parentPaper);

        CachedPaper cachedPaper = cacheService.createCachedPaper(research, paper, parentPaper, CachedPaperType.CITATION);

        List<CachedPaper> citations = cacheService.getCachedCitations(research, List.of(parentPaper));

        Assertions.assertThat(citations).isEqualTo(List.of(cachedPaper));
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
        user.setActiveResearch(research); //probably unnecessary
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

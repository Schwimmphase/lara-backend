package edu.kit.iti.scale.lara.backend.repositorytests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.*;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaperType;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;

@InMemoryTest
public class CacheRepositoryTests {

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResearchRepository researchRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CachedPaperRepository cachedPaperRepository;

    @Autowired
    PaperService paperService;


    @Test
    public void testFindByCachedPaperIdResearch() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id1", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        Paper parentPaper = new Paper("id2", "parentPaper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(parentPaper);

        CachedPaper cachedPaper = new CachedPaper(paper, parentPaper, research, CachedPaperType.CITATION);
        cachedPaperRepository.save(cachedPaper);

        Assertions.assertThat(cachedPaperRepository.findById(cachedPaper.getCachedPaperId()).isPresent()).isEqualTo(true);
        Assertions.assertThat(cachedPaperRepository.findById(cachedPaper.getCachedPaperId()).get()).isEqualTo(cachedPaper);

        Assertions.assertThat(cachedPaperRepository.findByCachedPaperIdResearch(research)).isEqualTo(List.of(cachedPaper));

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

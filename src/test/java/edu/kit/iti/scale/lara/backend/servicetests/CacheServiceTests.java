package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.*;
import edu.kit.iti.scale.lara.backend.controller.service.CacheService;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
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
    ApiActionController apiActionController;

    @Autowired
    CacheService cacheService;


//    @Test
//    public void theTest() {
//        User user = createUser();
//        Research research = createResearch(user);
//
//        try {
//            List<Paper> papers = apiActionController.getPapersByKeyword("Graph");
//            Paper paper1 = papers.get(0);
//            Paper paper2 = papers.get(1);
//
//            paperService.savePaperToDataBase(paper1);
//            paperService.savePaperToDataBase(paper2);
//
//            SavedPaper savedPaper1 = paperService.createSavedPaper(research, paper1, SaveState.ADDED);
//            SavedPaper savedPaper2 = paperService.createSavedPaper(research, paper2, SaveState.ADDED);
//
//            try {
//                cacheService.initializeCache(List.of(savedPaper1, savedPaper2), research);
//
//
//
//                List<CachedPaper> citations = cacheService.getCitations(research, List.of(paper1));
//                List<CachedPaper> references = cacheService.getReferences(research, List.of(paper1));
//                for (CachedPaper cachedPaper : citations) {
//                    Assertions.assertThat(cachedPaper.getType()).isEqualTo(CachedPaperType.CITATION);
//                    Assertions.assertThat(cachedPaper.getCachedPaperId().getParentPaper()).isEqualTo(paper1);
//                }
//
//                for (CachedPaper cachedPaper : references) {
//                    Assertions.assertThat(cachedPaper.getType()).isEqualTo(CachedPaperType.REFERENCE);
//                    Assertions.assertThat(cachedPaper.getCachedPaperId().getParentPaper()).isEqualTo(paper1);
//                }
//
//                cacheService.removePaper(paper1, research);
//
//                Assertions.assertThat(cacheService.getCitations(research, List.of(paper1)).isEmpty()).isEqualTo(true);
//                Assertions.assertThat(cacheService.getReferences(research, List.of(paper1)).isEmpty()).isEqualTo(true);
//
//                cacheService.flushCacheResearch(research);
//
//                Assertions.assertThat(cacheService.getReferences(research, List.of(paper1, paper2)).isEmpty()).isEqualTo(true);
//
//            } catch (IOException e) {
//                Assertions.fail("SemanticScholarApi didn´t work");
//            }
//
//        } catch (IOException e) {
//            Assertions.fail("IOException");
//        }
//
//    }

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

        List<CachedPaper> citations = cacheService.getCitations(research, List.of(parentPaper));

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

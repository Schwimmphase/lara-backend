package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.ResearchRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@InMemoryTest
public class PaperServiceTests {

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResearchRepository researchRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private PaperService paperService;


    @Test
    public void testSaveAndGetPaper() {
        Author author = createAuthor();

        Paper paper = new Paper("id", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        try {
            Paper returnedPaper = paperService.getPaper("id");
            Assertions.assertThat(returnedPaper).isEqualTo(paper);

        } catch (NotInDataBaseException e) {
            Assertions.fail("Failed to load Paper from Database");
        }
        boolean exceptionThrown = false;
        try {
            paperService.getPaper("0");
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }


    @Test
    public void testCreateAndGetSavedPaper() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        SavedPaper savedPaper = paperService.createSavedPaper(research, paper, SaveState.ADDED);

        try {
            SavedPaper returnedPaper = paperService.getSavedPaper(user, paper, research);
            Assertions.assertThat(returnedPaper).isEqualTo(savedPaper);

        } catch (NotInDataBaseException e) {
            Assertions.fail("Failed to load Paper from Database");
        } catch (WrongUserException e) {
            Assertions.fail("User didn´t save the Paper");
        }
        boolean exceptionThrown = false;
        try {
            Paper otherPaper = paperService.getPaper("0");
            System.out.println(otherPaper);
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }

    @Test
    public void testGetSavedPapers() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper1 = new Paper("id1", "paper1", 2023, "abstract1",
                0, 0, "venue1", "url1", List.of(author));
        paperService.savePaperToDataBase(paper1);

        Paper paper2 = new Paper("id2", "paper2", 2023, "abstract2",
                0, 0, "venue2", "url2", List.of(author));
        paperService.savePaperToDataBase(paper2);

        Paper paper3 = new Paper("id3", "paper3", 2023, "abstract3",
                0, 0, "venue3", "url3", List.of(author));
        paperService.savePaperToDataBase(paper3);

        SavedPaper savedPaper1 = paperService.createSavedPaper(research, paper1, SaveState.ADDED);
        SavedPaper savedPaper2 = paperService.createSavedPaper(research, paper2, SaveState.ENQUEUED);
        SavedPaper savedPaper3 = paperService.createSavedPaper(research, paper3, SaveState.HIDDEN);

        try {
            List<SavedPaper> savedPapers = paperService.getSavedPapers(research, user);
            Assertions.assertThat(savedPapers).isEqualTo(List.of(savedPaper1, savedPaper2, savedPaper3));
        } catch (WrongUserException e) {
            Assertions.fail("User isn´t allowed to access this research");
        }
    }

    @Test
    public void testTagsOnPaper() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        SavedPaper savedPaper = paperService.createSavedPaper(research, paper, SaveState.ADDED);

        Tag tag = new Tag("#0000FF", "Test-Tag", research);

        paperService.addTagToPaper(savedPaper, tag);
        Assertions.assertThat(savedPaper.getTags()).isEqualTo(List.of(tag));

        paperService.removeTagFromPaper(savedPaper, tag);
        Assertions.assertThat(savedPaper.getTags().isEmpty()).isEqualTo(true);
    }

    @Test
    public void testCommentPaper() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        SavedPaper savedPaper = paperService.createSavedPaper(research, paper, SaveState.ADDED);

        paperService.commentPaper(savedPaper, "test");

        Assertions.assertThat(savedPaper.getComment().getText()).isEqualTo("test");
    }

    @Test
    public void testChangeSaveState() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        SavedPaper savedPaper = paperService.createSavedPaper(research, paper, SaveState.ADDED);

        try {
            paperService.changeSaveState(savedPaper, SaveState.HIDDEN);
        } catch (IOException e) {
            Assertions.fail("Something with the SemanticScholarApi is wrong");
        }
        Assertions.assertThat(savedPaper.getSaveState()).isEqualTo(SaveState.HIDDEN);
    }

    @Test
    public void testRelevanceOnPaper() {
        User user = createUser();
        Research research = createResearch(user);
        Author author = createAuthor();

        Paper paper = new Paper("id", "paper", 2023, "abstract",
                0, 0, "venue", "url", List.of(author));
        paperService.savePaperToDataBase(paper);

        SavedPaper savedPaper = paperService.createSavedPaper(research, paper, SaveState.ADDED);

        paperService.setRelevanceOfPaper(savedPaper, 3);

        Assertions.assertThat(savedPaper.getRelevance()).isEqualTo(3);
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


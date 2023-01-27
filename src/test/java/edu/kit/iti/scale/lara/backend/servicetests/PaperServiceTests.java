package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.controller.repository.PaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.util.List;

@DataJpaTest
public class PaperServiceTests {

    @Autowired
    private  PaperService paperService;
    @Autowired
    private  PaperRepository paperRepository;
    @Autowired
    private  SavedPaperRepository savedPaperRepository;

    UserCategory category = new UserCategory("#0000FF", "Test-User");
    User user = new User("userOne", "password1", category);
    Research research = new Research("New-Research", new Comment("description"), ZonedDateTime.now(), user);
    Author author = new Author("TestAuthorId", "TestAuthorName");
    Paper paper1 = new Paper("1", "paper1", 2023, "abstract1",
            0, 0, "venue", "url1", List.of(author));
    Paper paper2 = new Paper("2", "paper2", 2023, "abstract2",
            0, 0, "venue", "url2", List.of(author));
    Paper paper3 = new Paper("3", "paper3", 2023, "abstract3",
            0, 0, "venue", "url3", List.of(author));
    Paper paper4 = new Paper("4", "paper4", 2023, "abstract4",
            0, 0, "venue", "url4", List.of(author));
    Paper paper5 = new Paper("5", "paper5", 2023, "abstract5",
            0, 0, "venue", "url5", List.of(author));


    @Test
    public void TestCreateAndGetPaper() {
        paperService.createSavedPaper(research, paper1, SaveState.ADDED);
        paperService.createSavedPaper(research, paper2, SaveState.ENQUEUED);
        paperService.createSavedPaper(research, paper3, SaveState.HIDDEN);

        try {
            Paper returnedPaper1 = paperService.getPaper("1");
            Paper returnedPaper2 = paperService.getPaper("2");
            Paper returnedPaper3 = paperService.getPaper("3");

            assert returnedPaper1.equals(paper1);
            assert returnedPaper2.equals(paper2);
            assert returnedPaper3.equals(paper3);
        } catch (NotInDataBaseException e) {
            System.out.println("Failed to load Paper from Database");
        }
    }
}

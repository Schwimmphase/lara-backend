package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.PersistentService;
import edu.kit.iti.scale.lara.backend.TestInstanceProvider;
import edu.kit.iti.scale.lara.backend.controller.service.PaperService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@InMemoryTest
public class PaperServiceTests {

    @Autowired
    private PaperService paperService;

    @Autowired
    private PersistentService persistentService;

    private final TestInstanceProvider tip = new TestInstanceProvider();

    @Test
    public void testSaveAndGetPaper() {
        paperService.savePaperToDataBase(tip.getPaper1());
        paperService.savePaperToDataBase(tip.getPaper2());
        paperService.savePaperToDataBase(tip.getPaper3());

        try {
            Paper returnedPaper1 = paperService.getPaper("1");
            Paper returnedPaper2 = paperService.getPaper("2");
            Paper returnedPaper3 = paperService.getPaper("3");

            Assertions.assertThat(returnedPaper1).isEqualTo(tip.getPaper1());
            Assertions.assertThat(returnedPaper2).isEqualTo(tip.getPaper2());
            Assertions.assertThat(returnedPaper3).isEqualTo(tip.getPaper3());
        } catch (NotInDataBaseException e) {
            System.out.println("Failed to load Paper from Database");
        }
        boolean exceptionThrown = false;
        try {
            paperService.getPaper("4");
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }


    @Test
    public void testCreateAndGetSavedPaper(@Autowired Research persistentResearch1, @Autowired Paper persistentPaper1,
                                           @Autowired Paper persistentPaper2, @Autowired Paper persistentPaper3,
                                           @Autowired User persistentUser1) {
        persistentService.persist(persistentResearch1, persistentPaper1, persistentPaper2, persistentPaper3, persistentUser1);

        SavedPaper savedPaper1 = paperService.createSavedPaper(persistentResearch1, persistentPaper1, SaveState.ADDED);
        SavedPaper savedPaper2 = paperService.createSavedPaper(persistentResearch1, persistentPaper2, SaveState.ENQUEUED);
        SavedPaper savedPaper3 = paperService.createSavedPaper(persistentResearch1, persistentPaper3, SaveState.HIDDEN);

        try {
            SavedPaper returnedPaper1 = paperService.getSavedPaper(persistentUser1, persistentPaper1, persistentResearch1);
            SavedPaper returnedPaper2 = paperService.getSavedPaper(persistentUser1, persistentPaper1, persistentResearch1);
            SavedPaper returnedPaper3 = paperService.getSavedPaper(persistentUser1, persistentPaper1, persistentResearch1);

            Assertions.assertThat(returnedPaper1).isEqualTo(savedPaper1);
            Assertions.assertThat(returnedPaper2).isEqualTo(savedPaper2);
            Assertions.assertThat(returnedPaper3).isEqualTo(savedPaper3);
        } catch (NotInDataBaseException e) {
            System.out.println("Failed to load Paper from Database");
        } catch (WrongUserException e) {
            System.out.println("User did´nt saved the Paper");
        }
        boolean exceptionThrown = false;
        try {
            Paper paper4 = paperService.getPaper("4");
            System.out.println(paper4);
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }

    @Test
    public void testGetSavedPapers(@Autowired Research persistentResearch1, @Autowired Paper persistentPaper1,
                                   @Autowired Paper persistentPaper2, @Autowired Paper persistentPaper3) {
        persistentService.persist(persistentResearch1, persistentPaper1, persistentPaper2, persistentPaper3);

        SavedPaper savedPaper1 = paperService.createSavedPaper(persistentResearch1, persistentPaper1, SaveState.ADDED);
        SavedPaper savedPaper2 = paperService.createSavedPaper(persistentResearch1, persistentPaper2, SaveState.ENQUEUED);
        SavedPaper savedPaper3 = paperService.createSavedPaper(persistentResearch1, persistentPaper3, SaveState.HIDDEN);

        try {
            List<SavedPaper> savedPapers = paperService.getSavedPapers(persistentResearch1, tip.getUser1());
            Assertions.assertThat(savedPapers).isEqualTo(List.of(savedPaper1, savedPaper2, savedPaper3));
        } catch (WrongUserException e) {
            System.out.println("User is´nt allowed to access this research");
        }
    }


    @Test
    public void testTagsOnPaper(@Autowired Research persistentResearch1, @Autowired Paper persistentPaper1) {
        persistentService.persist(persistentResearch1, persistentPaper1);

        SavedPaper paper = paperService.createSavedPaper(persistentResearch1, persistentPaper1, SaveState.ADDED);

        Tag tag = new Tag("#0000FF", "Test-Tag", persistentResearch1);

        paperService.addTagToPaper(paper, tag);
        Assertions.assertThat(paper.getTags()).isEqualTo(List.of(tag));

        paperService.removeTagFromPaper(paper, tag);
        Assertions.assertThat(paper.getTags().isEmpty()).isEqualTo(true);
    }

    @Test
    public void testCommentPaper(@Autowired Research persistentResearch1, @Autowired Paper persistentPaper1) {
        persistentService.persist(persistentResearch1, persistentPaper1);

        SavedPaper paper = paperService.createSavedPaper(persistentResearch1, persistentPaper1, SaveState.ADDED);

        paperService.commentPaper(paper, "test");

        Assertions.assertThat(paper.getComment().getText()).isEqualTo("test");
    }

    @Test
    public void testChangeSaveState(@Autowired Research persistentResearch1, @Autowired Paper persistentPaper1) {
        persistentService.persist(persistentResearch1, persistentPaper1);

        SavedPaper paper = paperService.createSavedPaper(persistentResearch1, persistentPaper1, SaveState.ADDED);

        paperService.changeSaveState(paper, SaveState.HIDDEN);

        Assertions.assertThat(paper.getSaveState()).isEqualTo(SaveState.HIDDEN);
    }

    @Test
    public void testRelevanceOnPaper(@Autowired Research persistentResearch1, @Autowired Paper persistentPaper1) {
        persistentService.persist(persistentResearch1, persistentPaper1);

        SavedPaper paper = paperService.createSavedPaper(persistentResearch1, persistentPaper1, SaveState.ADDED);

        paperService.setRelevanceOfPaper(paper, 3);

        Assertions.assertThat(paper.getRelevance()).isEqualTo(3);
    }
}

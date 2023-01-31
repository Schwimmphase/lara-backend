package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.PersistentService;
import edu.kit.iti.scale.lara.backend.controller.repository.ResearchRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@InMemoryTest
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    @Autowired
    PersistentService persistentService;

    @Autowired
    ResearchRepository researchRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    public void createAndGetTag(@Autowired Research persistentResearch1, @Autowired User persistentUser1) {
        persistentService.persist(persistentResearch1, persistentUser1);
        researchRepository.save(persistentResearch1);
        userRepository.save(persistentUser1);

        Tag tag1 = tagService.createTag("#FF0000", "Test-tag-1", persistentResearch1);
        Tag tag2 = tagService.createTag("#00FF00", "Test-tag-2", persistentResearch1);
        Tag tag3 = tagService.createTag("#0000FF", "Test-tag-3", persistentResearch1);

        try {
            Tag returnedTag1 = tagService.getTag(tag1.getId(), persistentUser1);
            Tag returnedTag2 = tagService.getTag(tag2.getId(), persistentUser1);
            Tag returnedTag3 = tagService.getTag(tag3.getId(), persistentUser1);

            Assertions.assertThat(returnedTag1).isEqualTo(tag1);
            Assertions.assertThat(returnedTag2).isEqualTo(tag2);
            Assertions.assertThat(returnedTag3).isEqualTo(tag3);

        } catch (NotInDataBaseException e) {
            System.out.println("Failed to load Tag from Database");
        } catch (WrongUserException e) {
            Assertions.fail("wrong User");
        }
        boolean exceptionThrown = false;
        try {
            tagService.getTag("4", persistentUser1);
        } catch (NotInDataBaseException ee) {
            exceptionThrown = true;
        } catch (WrongUserException eee) {
            System.out.println("User has no Tag with given Id2");
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
    }


    @Test
    public void testGetTags(@Autowired Research persistentResearch1) {
        Tag tag1 = tagService.createTag("#FF0000", "Test-tag-1", persistentResearch1);
        Tag tag2 = tagService.createTag("#00FF00", "Test-tag-2", persistentResearch1);
        Tag tag3 = tagService.createTag("#0000FF", "Test-tag-3", persistentResearch1);

        List<Tag> tags = tagService.getTags(persistentResearch1);

        Assertions.assertThat(tags).isEqualTo(List.of(tag1, tag2, tag3));
    }

    @Test
    public void testUpdateTag(@Autowired Research persistentResearch1) {
        Tag tag = tagService.createTag("#FF0000", "Test-tag", persistentResearch1);

        tagService.updateTag(tag, "New-Name", "#0000FF");

        Assertions.assertThat(tag.getColor()).isEqualTo("#0000FF");
        Assertions.assertThat(tag.getName()).isEqualTo("New-Name");
    }

    @Test
    public void testDeleteTag(@Autowired Research persistentResearch1, @Autowired User persistentUser1) {
        Tag tag = tagService.createTag("#FF0000", "Test-tag", persistentResearch1);

        try {
            Assertions.assertThat(tagService.getTag(tag.getId(), persistentUser1)).isEqualTo(tag);
        } catch (WrongUserException e) {
            System.out.println("Wrong User1");
        } catch (NotInDataBaseException ee) {
            System.out.println("Not in Database1");
        }

        tagService.deleteTag(tag);

        boolean exceptionThrown = false;

        try {
            tagService.getTag(tag.getId(), persistentUser1);
        } catch (NotInDataBaseException ee) {
            exceptionThrown = true;
        } catch (WrongUserException eee) {
            System.out.println("Wrong user2");
        }

        Assertions.assertThat(exceptionThrown).isEqualTo(true);

    }
}

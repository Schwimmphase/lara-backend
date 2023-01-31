package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@InMemoryTest
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    @Autowired
    private Research persistentResearch1;
    @Autowired
    private User persistentUser1;

    @Test
    public void createAndGetTag() {
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
            System.out.println("User has no Tag with given Id");
            boolean exceptionThrown = false;
            try {
                tagService.getTag("4", persistentUser1);
            } catch (NotInDataBaseException ee) {
                exceptionThrown = true;
            } catch (WrongUserException eee) {
                System.out.println("User has no Tag with given Id");
            }
            Assertions.assertThat(exceptionThrown).isEqualTo(true);
        }
    }

    @Test
    public void testGetTags() {
        Tag tag1 = tagService.createTag("#FF0000", "Test-tag-1", persistentResearch1);
        Tag tag2 = tagService.createTag("#00FF00", "Test-tag-2", persistentResearch1);
        Tag tag3 = tagService.createTag("#0000FF", "Test-tag-3", persistentResearch1);

        List<Tag> tags = tagService.getTags(persistentResearch1);

        Assertions.assertThat(tags).isEqualTo(List.of(tag1, tag2, tag3));
    }

    @Test
    public void testUpdateTag() {
        Tag tag = tagService.createTag("#FF0000", "Test-tag", persistentResearch1);

        tagService.updateTag(tag, "New-Name", "#0000FF");

        Assertions.assertThat(tag.getColor()).isEqualTo("#0000FF");
        Assertions.assertThat(tag.getName()).isEqualTo("New-Name");
    }

    @Test
    public void testDeleteTag() {
        Tag tag = tagService.createTag("#FF0000", "Test-tag", persistentResearch1);

        try {
            Assertions.assertThat(tagService.getTag(tag.getId(), persistentUser1)).isEqualTo(tag);
        } catch (WrongUserException | NotInDataBaseException e) {
            System.out.println("This shouldn´t happen");
        }

        tagService.deleteTag(tag);

        boolean exceptionThrown = false;

        try {
            tagService.getTag(tag.getId(), persistentUser1);
        } catch (NotInDataBaseException ee) {
            exceptionThrown = true;
        } catch (WrongUserException eee) {
            System.out.println("Wrong user");
        }

        Assertions.assertThat(exceptionThrown).isEqualTo(true);

    }
}

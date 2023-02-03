package edu.kit.iti.scale.lara.backend.servicetests;

import edu.kit.iti.scale.lara.backend.InMemoryTest;
import edu.kit.iti.scale.lara.backend.controller.repository.AuthorRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.ResearchRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserCategoryRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.controller.service.TagService;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;

@InMemoryTest
public class TagServiceTests {

    @Autowired
    UserCategoryRepository userCategoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResearchRepository researchRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    TagService tagService;

    @Test
    public void testCreateAndGetTag() {
        User user = createUser();
        Research research = createResearch(user);

        Tag tag = tagService.createTag("0000FF", "test-tag", research);

        try {
            Assertions.assertThat(tagService.getTag(tag.getId(), user)).isEqualTo(tag);

        } catch (NotInDataBaseException e) {
            Assertions.fail("Not in database");
        } catch (WrongUserException e) {
            Assertions.fail("wrong user");
        }
    }

    @Test
    public void testGetTags() {
        User user = createUser();
        Research research = createResearch(user);

        Tag tag1 = tagService.createTag("0000FF", "test-tag1", research);
        Tag tag2 = tagService.createTag("0000FF", "test-tag2", research);
        Tag tag3 = tagService.createTag("0000FF", "test-tag3", research);

        Assertions.assertThat(tagService.getTags(research)).isEqualTo(List.of(tag1, tag2, tag3));
    }

    @Test
    public void testUpdateTag() {
        User user = createUser();
        Research research = createResearch(user);

        Tag tag = tagService.createTag("0000FF", "test-tag", research);

        tagService.updateTag(tag, "new-name", "00FF00");

        Assertions.assertThat(tag.getName()).isEqualTo("new-name");
        Assertions.assertThat(tag.getColor()).isEqualTo("00FF00");
    }

    @Test
    public void testDeleteTag() {
        User user = createUser();
        Research research = createResearch(user);

        Tag tag = tagService.createTag("0000FF", "test-tag", research);

        tagService.deleteTag(tag);

        boolean exceptionThrown = false;
        try {
           tagService.getTag(tag.getId(), user);
        } catch (WrongUserException e) {
            Assertions.fail("Wrong user");
        } catch (NotInDataBaseException e) {
            exceptionThrown = true;
        }
        Assertions.assertThat(exceptionThrown).isEqualTo(true);
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

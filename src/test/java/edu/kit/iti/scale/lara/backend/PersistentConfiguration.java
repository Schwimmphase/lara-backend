package edu.kit.iti.scale.lara.backend;

import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.ZonedDateTime;
import java.util.List;

@Configuration
public class PersistentConfiguration {

    @Bean(name = "persistentCategoryBlue")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserCategory getCategoryBlue() {
        return new UserCategory("#0000FF", "Blue-User");
    }
    @Bean(name = "persistentCategoryRed")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public UserCategory getCategoryRed() {
        return new UserCategory("#FF0000", "Red-User");
    }


    @Bean(name = "persistentUser1")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User getUser1(UserCategory persistentCategoryBlue) {
        return new User("userOne", "password1", persistentCategoryBlue);
    }

    @Bean(name = "persistentUser2")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User getUser2(UserCategory persistentCategoryRed) {
        return new User("userTwo", "password1", persistentCategoryRed);
    }

    @Bean(name = "persistentUser3")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User getUser3(UserCategory persistentCategoryBlue) {
        return new User("userThree", "password1", persistentCategoryBlue);
    }

    @Bean(name = "persistentUser4")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User getUser4(UserCategory persistentCategoryRed) {
        return new User("userFour", "password1", persistentCategoryRed);
    }

    @Bean(name = "persistentUser5")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User getUser5(UserCategory persistentCategoryBlue) {
        return new User("userFive", "password1", persistentCategoryBlue);
    }


    @Bean(name = "persistentResearch1")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Research getResearch1(User persistentUser1) {
        return new Research("ResearchOne", new Comment("descriptionOne"), ZonedDateTime.now(), persistentUser1);
    }

    @Bean(name = "persistentResearch2")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Research getResearch2(User persistentUser2) {
        return new Research("ResearchTwo", new Comment("descriptionTwo"), ZonedDateTime.now(), persistentUser2);
    }

    @Bean(name = "persistentResearch3")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Research getResearch3(User persistentUser3) {
        return new Research("ResearchThree", new Comment("descriptionThree"), ZonedDateTime.now(), persistentUser3);
    }

    @Bean(name = "persistentResearch4")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Research getResearch4(User persistentUser4) {
        return new Research("ResearchFour", new Comment("descriptionFour"), ZonedDateTime.now(), persistentUser4);
    }

    @Bean(name = "persistentResearch5")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Research getResearch5(User persistentUser5) {
        return new Research("ResearchFive", new Comment("descriptionFive"), ZonedDateTime.now(), persistentUser5);
    }


    @Bean(name = "persistentAuthor1")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Author getAuthor1() {
        return new Author("TestAuthorId1", "TestAuthorPaul");
    }

    @Bean(name = "persistentAuthor2")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Author getAuthor2() {
        return new Author("TestAuthorId2", "TestAuthorJohannes");
    }

    @Bean(name = "persistentAuthor3")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Author getAuthor3() {
        return new Author("TestAuthorId3", "TestAuthorGregor");
    }

    @Bean(name = "persistentAuthor4")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Author getAuthor4() {
        return new Author("TestAuthorId4", "TestAuthorThomas");
    }

    @Bean(name = "persistentAuthor5")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Author getAuthor5() {
        return new Author("TestAuthorId5", "TestAuthorLinus");
    }


    @Bean(name = "persistentPaper1")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Paper getPaper1(Author persistentAuthor1) {
        return new Paper("1", "paper1", 2023, "abstract1",
                0, 0, "venue1", "url1", List.of(persistentAuthor1));
    }

    @Bean(name = "persistentPaper2")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Paper getPaper2(Author persistentAuthor2, Author persistentAuthor3) {
        return new Paper("2", "paper2", 2023, "abstract2",
                0, 0, "venue2", "url2", List.of(persistentAuthor2, persistentAuthor3));
    }

    @Bean(name = "persistentPaper3")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Paper getPaper3(Author persistentAuthor4) {
        return new Paper("3", "paper3", 2023, "abstract3",
                0, 0, "venue3", "url3", List.of(persistentAuthor4));
    }

    @Bean(name = "persistentPaper4")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Paper getPaper4(Author persistentAuthor1, Author persistentAuthor3, Author persistentAuthor5) {
        return new Paper("4", "paper4", 2023, "abstract4",
                0, 0, "venue4", "url4", List.of(persistentAuthor1, persistentAuthor3, persistentAuthor5));
    }

    @Bean(name = "persistentPaper5")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Paper getPaper5(Author persistentAuthor3) {
        return new Paper("5", "paper5", 2023, "abstract5",
                0, 0, "venue5", "url5", List.of(persistentAuthor3));
    }

}

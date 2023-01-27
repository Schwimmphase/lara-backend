package edu.kit.iti.scale.lara.backend;

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

import java.time.ZonedDateTime;
import java.util.List;

public class TestInstanceProvider {

    private UserCategory categoryBlue = new UserCategory("#0000FF", "Blue-User");
    private UserCategory categoryRed = new UserCategory("#FF0000", "Red-User");

    private User user1 = new User("userOne", "password1", categoryBlue);
    private User user2 = new User("userTwo", "password1", categoryRed);
    private User user3 = new User("userThree", "password1", categoryBlue);
    private User user4 = new User("userFour", "password1", categoryRed);
    private User user5 = new User("userFive", "password1", categoryBlue);

    private Research research1 = new Research("ResearchOne", new Comment("descriptionOne"), ZonedDateTime.now(), user1);
    private Research research2 = new Research("ResearchTwo", new Comment("descriptionTwo"), ZonedDateTime.now(), user2);
    private Research research3 = new Research("ResearchThree", new Comment("descriptionThree"), ZonedDateTime.now(), user3);
    private Research research4 = new Research("ResearchFour", new Comment("descriptionFour"), ZonedDateTime.now(), user4);
    private Research research5 = new Research("ResearchFive", new Comment("descriptionFive"), ZonedDateTime.now(), user5);

    private Author author1 = new Author("TestAuthorId1", "TestAuthorPaul");
    private Author author2 = new Author("TestAuthorId2", "TestAuthorJohannes");
    private Author author3 = new Author("TestAuthorId3", "TestAuthorGregor");
    private Author author4 = new Author("TestAuthorId4", "TestAuthorThomas");
    private Author author5 = new Author("TestAuthorId5", "TestAuthorLinus");


    private Paper paper1 = new Paper("1", "paper1", 2023, "abstract1",
            0, 0, "venue1", "url1", List.of(author1));
    private Paper paper2 = new Paper("2", "paper2", 2023, "abstract2",
            0, 0, "venue2", "url2", List.of(author2, author3));
    private Paper paper3 = new Paper("3", "paper3", 2023, "abstract3",
            0, 0, "venue3", "url3", List.of(author4));
    private Paper paper4 = new Paper("4", "paper4", 2023, "abstract4",
            0, 0, "venue4", "url4", List.of(author1, author3, author5));
    private Paper paper5 = new Paper("5", "paper5", 2023, "abstract5",
            0, 0, "venue5", "url5", List.of(author3));



    public UserCategory getCategoryBlue() {
        return categoryBlue;
    }

    public void setCategoryBlue(UserCategory categoryBlue) {
        this.categoryBlue = categoryBlue;
    }

    public UserCategory getCategoryRed() {
        return categoryRed;
    }

    public void setCategoryRed(UserCategory categoryRed) {
        this.categoryRed = categoryRed;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public User getUser3() {
        return user3;
    }

    public void setUser3(User user3) {
        this.user3 = user3;
    }

    public User getUser4() {
        return user4;
    }

    public void setUser4(User user4) {
        this.user4 = user4;
    }

    public User getUser5() {
        return user5;
    }

    public void setUser5(User user5) {
        this.user5 = user5;
    }

    public Research getResearch1() {
        return research1;
    }

    public void setResearch1(Research research1) {
        this.research1 = research1;
    }

    public Research getResearch2() {
        return research2;
    }

    public void setResearch2(Research research2) {
        this.research2 = research2;
    }

    public Research getResearch3() {
        return research3;
    }

    public void setResearch3(Research research3) {
        this.research3 = research3;
    }

    public Research getResearch4() {
        return research4;
    }

    public void setResearch4(Research research4) {
        this.research4 = research4;
    }

    public Research getResearch5() {
        return research5;
    }

    public void setResearch5(Research research5) {
        this.research5 = research5;
    }

    public Author getAuthor1() {
        return author1;
    }

    public void setAuthor1(Author author1) {
        this.author1 = author1;
    }

    public Author getAuthor2() {
        return author2;
    }

    public void setAuthor2(Author author2) {
        this.author2 = author2;
    }

    public Author getAuthor3() {
        return author3;
    }

    public void setAuthor3(Author author3) {
        this.author3 = author3;
    }

    public Author getAuthor4() {
        return author4;
    }

    public void setAuthor4(Author author4) {
        this.author4 = author4;
    }

    public Author getAuthor5() {
        return author5;
    }

    public void setAuthor5(Author author5) {
        this.author5 = author5;
    }

    public Paper getPaper1() {
        return paper1;
    }

    public void setPaper1(Paper paper1) {
        this.paper1 = paper1;
    }

    public Paper getPaper2() {
        return paper2;
    }

    public void setPaper2(Paper paper2) {
        this.paper2 = paper2;
    }

    public Paper getPaper3() {
        return paper3;
    }

    public void setPaper3(Paper paper3) {
        this.paper3 = paper3;
    }

    public Paper getPaper4() {
        return paper4;
    }

    public void setPaper4(Paper paper4) {
        this.paper4 = paper4;
    }

    public Paper getPaper5() {
        return paper5;
    }

    public void setPaper5(Paper paper5) {
        this.paper5 = paper5;
    }
}

package edu.kit.iti.scale.lara.backend;

import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class TestObjects {

    public static User user() {
        return user("id12345", "test-user", "password", userCategory());
    }

    public static User user(String id, String username, String password, UserCategory userCategory) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(username, encoder.encode(password), userCategory);
        user.setId(id);
        return user;
    }

    public static UserCategory userCategory() {
        return userCategory("id12345", "test-category", "color");
    }

    public static UserCategory userCategory(String id, String name, String color) {
        UserCategory userCategory = new UserCategory(color, name);
        userCategory.setId(id);
        return userCategory;
    }

    public static Research research() {
        return research("id12345", "test-research", "description", user());
    }

    public static Research research(String id, String name, String description, User user) {
        ZonedDateTime started = ZonedDateTime.of(LocalDateTime.of(2022, 1, 1, 0, 0),
                TimeZone.getDefault().toZoneId());
        Research research = new Research(name, new Comment(description), started, user);
        research.setId(id);
        return research;
    }

    public static Tag tag(String id, String name, String color) {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        tag.setColor(color);
        tag.setResearch(research());
        return tag;
    }

    public static Tag tag() {
        return tag("id12345", "test-tag", "test-color");
    }

}

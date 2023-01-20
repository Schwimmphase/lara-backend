package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class TagService {

    public Tag createTag(User user, String name, Research research) {

        // TODO

        return null;
    }

    public List<Tag> getTags(Research research, User user) {

        // TODO

        return null;
    }

    public Tag updateTag(String tagId, User user, String newName, Color newColor) {

        // TODO

        return null;
    }

    public void deleteTag(String tagId, User user) {

        // TODO

    }
}

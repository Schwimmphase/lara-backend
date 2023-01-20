package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.TagRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    TagRepository tagRepository;

    public Tag createTag(String color, String name, Research research) {
        Tag tag = new Tag(color, name, research);
        tagRepository.save(tag);
        return tag;
    }

    public Tag getTag(String tagId, User user) throws NotInDataBaseException, WrongUserException {
        if (tagRepository.findById(tagId).isPresent()) {
            Tag tag = tagRepository.findById(tagId).get();
            if (user.getResearches().contains(tag.getResearch())) {
                return tag;
            } else {
                throw new WrongUserException();
            }
        } else {
            throw new NotInDataBaseException();
        }
    }

    public List<Tag> getTags(Research research) {
        return tagRepository.findByResearch(research);
    }

    public Tag updateTag(Tag tag, String newName, String newColor) {
        tag.setName(newName);
        tag.setColor(newColor);
        return null;
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }
}

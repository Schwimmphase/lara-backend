package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.TagRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used to handle everything about tags
 *
 * @author ukgcc
 */
@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final SavedPaperRepository savedPaperRepository;

    /**
     * Creates a new Tag and saves it in the {@link TagRepository}.
     *
     * @param color    the color of the tag in hex as a String
     * @param name     the name of the tag
     * @param research the research the tag belongs to
     * @return the created tag
     */
    public Tag createTag(String color, String name, Research research) {
        Tag tag = new Tag(color, name, research);
        tagRepository.save(tag);
        return tag;
    }

    /**
     * Returns the tag with the given id if it belongs to the users active research.
     *
     * @param tagId the id of the tag
     * @param user  the user who requested this tag
     * @return the tag
     * @throws NotInDataBaseException when there is no tag with this id
     * @throws WrongUserException     when the users active research is different to the research the tag belongs to
     */
    public Tag getTag(String tagId, User user) throws NotInDataBaseException, WrongUserException {
        if (tagRepository.findById(tagId).isPresent()) {
            Tag tag = tagRepository.findById(tagId).get();
            if (user.getActiveResearch().equals(tag.getResearch())) {
                return tag;
            } else {
                throw new WrongUserException();
            }
        } else {
            throw new NotInDataBaseException();
        }
    }

    /**
     * Finds all tags that belong to a certain research.
     *
     * @param research the research whose tags are wanted
     * @return the tags that belong to this research
     */
    public List<Tag> getTags(Research research) {
        return tagRepository.findByResearch(research);
    }

    /**
     * Updates a tag by assigning a new name and a new color.
     *
     * @param tag      the tag to be updated
     * @param newName  the new name
     * @param newColor the new color
     * @return the updated tag
     */
    public Tag updateTag(Tag tag, String newName, String newColor) {
        tag.setName(newName);
        tag.setColor(newColor);
        tagRepository.save(tag);
        return tag;
    }

    /**
     * Deletes a tag from the {@link TagRepository}.
     *
     * @param tag the tag to be deleted
     */
    public void deleteTag(Tag tag, Research research) throws IllegalArgumentException {
        List<SavedPaper> researchPapers = savedPaperRepository.findBySavedPaperIdResearch(research);
        for (SavedPaper savedPaper : researchPapers) {
            if (savedPaper.getTags().contains(tag)) {
                throw new IllegalArgumentException();
            }
        }
        tagRepository.delete(tag);
    }
}
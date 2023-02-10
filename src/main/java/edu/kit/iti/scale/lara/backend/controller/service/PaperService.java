package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.CachedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.PaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.Tag;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * This class is used to manage all Papers and SavedPapers
 *
 * @author ukgcc
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PaperService {

    private final PaperRepository paperRepository;
    private final SavedPaperRepository savedPaperRepository;
    private final CachedPaperRepository cachedPaperRepository;
    private final RecommendationService recommendationService;
    private final ApiActionController apiActionController;

    /**
     * Saves a paper to the PaperRepository
     *
     * @param paper the paper to be saved
     */
    public void savePaperToDataBase(Paper paper) {
        if (paperRepository.existsById(paper.getPaperId())) {
            paperRepository.deleteById(paper.getPaperId());
        }

        paperRepository.save(paper);
    }

    /**
     * Finds the paper with given id in the PaperRepository
     *
     * @param id the id of the paper
     * @return the paper with this id
     * @throws NotInDataBaseException when there is no paper with this id
     */
    public Paper getPaper(String id) throws NotInDataBaseException {
        return getPaper(id, false);
    }

    /**
     * Finds the paper with given id in the PaperRepository or gets it from ApiActionController if lookInApi is true
     *
     * @param id        the id of the paper
     * @param lookInApi indicates whether we should try to get the paper with this id from the ApiActionController or not
     * @return the paper with this id
     * @throws NotInDataBaseException when there is no paper with this id in the PaperRepository
     *                                or the ApiActionController couldn't get a paper with this id
     */
    public Paper getPaper(String id, boolean lookInApi) throws NotInDataBaseException {
        if (paperRepository.findById(id).isPresent()) {
            return paperRepository.findById(id).get();
        } else {
            if (!lookInApi)
                throw new NotInDataBaseException("Paper not in database");

            try {
                Paper paper = apiActionController.getPaper(id);
                savePaperToDataBase(paper);
                return paper;
            } catch (IOException e) {
                throw new NotInDataBaseException("Could not get paper from API", e);
            }
        }

    }

    /**
     * Creates a savedPaper and potentially informs the RecommendationService that a paper was added
     *
     * @param research  the research the SavedPaper belongs to
     * @param paper     the Paper the SavedPaper points to
     * @param saveState the SaveState of the SavedPaper
     * @return the SavedPaper
     * @throws IOException
     */
    public SavedPaper createSavedPaper(Research research, Paper paper, SaveState saveState) throws IOException {
        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment(""), 0, saveState);
        savedPaperRepository.save(savedPaper);
        if (saveState.equals(SaveState.ADDED)) {
            recommendationService.paperAdded(research, paper);
        }
        return savedPaper;
        //savedPaper isn´t saved in research.savedPapers to avoid duplicates

    }

    /**
     * Deletes a savedPaper and informs the recommendationService that a paper was removed from the research
     * Also checks if there are Papers in the PaperRepository that have no Saved- or CachedPaper that points to them and
     * deletes them in this case
     *
     * @param research   the research the SavedPaper was deleted from
     * @param savedPaper the savedPaper that was deleted
     */
    public void deleteSavedPaper(Research research, SavedPaper savedPaper) {
        Paper paper = savedPaper.getSavedPaperId().getPaper();
        research.removeSavedPaper(savedPaper);
        savedPaperRepository.delete(savedPaper);
        recommendationService.paperRemoved(research, savedPaper.getSavedPaperId().getPaper());

        //deletes the paper as well if there is no other Cache- or SavedPaper that points to it
        if (savedPaperRepository.countBySavedPaperIdPaper(paper) == 0 &&
                cachedPaperRepository.countByCachedPaperIdPaper(paper) == 0 &&
                cachedPaperRepository.countByCachedPaperIdParentPaper(paper) == 0) {
            paperRepository.delete(paper);
        }
    }

    /**
     * Returns the SavedPaper with the id that is made by the paper and the research if the User is the one who created the SavedPaper
     *
     * @param user     the user who requested the SavedPaper
     * @param paper    the paper the SavedPaper holds
     * @param research the research the SavedPaper belongs to
     * @return the saved Paper
     * @throws WrongUserException     if the user doesn't have the research open that the SavedPaper belongs to
     * @throws NotInDataBaseException if no SavedPaper with this id exists
     */
    public SavedPaper getSavedPaper(User user, Paper paper, Research research) throws WrongUserException, NotInDataBaseException {
        SavedPaper.SavedPaperId id = new SavedPaper.SavedPaperId(paper, research);

        if (savedPaperRepository.findById(id).isPresent()) {
            SavedPaper savedPaper = savedPaperRepository.findById(id).get();
            if (user.getActiveResearch().equals(savedPaper.getSavedPaperId().getResearch())) {
                return savedPaper;
            } else {
                throw new WrongUserException();
            }
        } else throw new NotInDataBaseException();
    }

    /**
     * Returns all SavedPapers that belong to the research
     *
     * @param research the research whose SavedPapers are to be found
     * @param user     the user who requested the SavedPapers
     * @return all SavedPapers that belong to the research
     * @throws WrongUserException if the user doesn't have the research open that the SavedPaper belongs to
     */
    public List<SavedPaper> getSavedPapers(Research research, User user) throws WrongUserException {
        if (user.getActiveResearch().equals(research)) {
            return savedPaperRepository.findBySavedPaperIdResearch(research);
        } else {
            throw new WrongUserException();
        }
    }

    /**
     * Returns all Papers that are added to a research
     *
     * @param research the research whose added papers we want to get
     * @param user     the user who requested the added papers
     * @return the added papers
     * @throws WrongUserException if the user doesn't have the research open that the SavedPaper belongs to
     */
    public List<Paper> getAddedPapers(Research research, User user) throws WrongUserException {
        if (user.getActiveResearch().equals(research)) {
            List<SavedPaper> addedPapers = savedPaperRepository.findBySavedPaperIdResearch(research);
            addedPapers.removeIf(savedPaper -> savedPaper.getSaveState() != SaveState.ADDED);
            return addedPapers.stream().map(savedPaper -> savedPaper.getSavedPaperId().getPaper()).toList();
        } else {
            throw new WrongUserException();
        }
    }

    /**
     * Adds a tag to a SavedPaper
     *
     * @param savedPaper the SavedPaper
     * @param tag        the Tag to be added
     */
    public void addTagToPaper(SavedPaper savedPaper, Tag tag) {
        savedPaper.getTags().add(tag);
        savedPaperRepository.save(savedPaper);
    }

    /**
     * Removes a Tag from a SavedPaper
     *
     * @param savedPaper the SavedPaper
     * @param tag        the Tag to be removed
     */
    public void removeTagFromPaper(SavedPaper savedPaper, Tag tag) {
        savedPaper.getTags().remove(tag);
        savedPaperRepository.save(savedPaper);
    }

    /**
     * Set the Content of the Comment of a SavedPaper
     *
     * @param savedPaper the SavedPaper
     * @param comment    the content to be saved in the comment
     */
    public void commentPaper(SavedPaper savedPaper, String comment) {
        savedPaper.getComment().setText(comment);
        savedPaperRepository.save(savedPaper);
    }

    /**
     * Changes the SaveState of a SavedPaper and informs the RecommendationService about relevant changes
     *
     * @param savedPaper the SavedPaper whose SaveState is to be changed
     * @param saveState  the new SaveState
     * @throws IOException
     */
    public void changeSaveState(SavedPaper savedPaper, SaveState saveState) throws IOException {
        savedPaper.setSaveState(saveState);
        savedPaperRepository.save(savedPaper);

        //recommendationService has to tell cacheService to cache related papers
        if (saveState.equals(SaveState.ADDED)) {
            recommendationService.paperAdded(savedPaper.getSavedPaperId().getResearch(), savedPaper.getPaper());
        }
        //recommendationService has to tell cacheService to delete related papers
        if (saveState.equals(SaveState.HIDDEN)) {
            recommendationService.paperRemoved(savedPaper.getSavedPaperId().getResearch(), savedPaper.getPaper());
        }
    }

    /**
     * Sets the relevance of a SavedPaper
     *
     * @param savedPaper the SavedPaper
     * @param relevance  the new relevance
     */
    public void setRelevanceOfPaper(SavedPaper savedPaper, int relevance) {
        if (relevance < 0 || relevance > 3) {
            throw new IllegalArgumentException("The relevance of a paper has to be between 0-3");
        }
        savedPaper.setRelevance(relevance);
        savedPaperRepository.save(savedPaper);
    }
}

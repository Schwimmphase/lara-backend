package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.ResearchRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.UserRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages everything that falls under the context of a research
 *
 * @author ukgcc
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ResearchService {

    private final ResearchRepository researchRepository;
    private final ApiActionController apiActionController;
    private final RecommendationService recommendationService;
    private final SavedPaperRepository savedPaperRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new research and saves it in the Research Repository
     *
     * @param user        the user who created the research
     * @param title       the title of the research
     * @param description the description of the research
     * @return the created research
     */
    public Research createResearch(User user, String title, String description) {
        Research research = new Research(title, new Comment(description), ZonedDateTime.now(), user);
        researchRepository.save(research);
        return research;
    }

    /**
     * Finds a research with the given in the Research Repository and checks if the user is the one
     * the research belongs to
     *
     * @param researchId the id of the research
     * @param user       the user
     * @return the research with this id
     * @throws NotInDataBaseException when there is no research with this id
     * @throws WrongUserException     when the research was created by a different user
     */
    public Research getResearch(String researchId, User user) throws NotInDataBaseException, WrongUserException {
        if (researchRepository.findById(researchId).isPresent()) {
            Research research = researchRepository.findById(researchId).get();
            if (researchRepository.findByUser(user).contains(research)) {
                return research;
            } else {
                throw new WrongUserException();
            }
        } else {
            throw new NotInDataBaseException();
        }
    }

    /**
     * Finds all researches a user created
     *
     * @param user the user whose researches are to be found
     * @return the researches of the user
     */
    public List<Research> getResearches(User user) {
        return researchRepository.findByUser(user);
    }

    /**
     * Updates a research by assigning a new title and a new description
     *
     * @param research       the research to be updated
     * @param newTitle       the new title
     * @param newDescription the new description
     */
    public void updateResearch(Research research, String newTitle, String newDescription) {
        research.setTitle(newTitle);
        research.setDescription(new Comment(newDescription));
        researchRepository.save(research);
    }

    /**
     * Deletes a research from the Research ResearchRepository and sets the active research for the user to null
     * if it was the deleted research
     *
     * @param research the research to be deleted
     * @param user     the user the research belongs to
     */
    public void deleteResearch(Research research, User user) {
        if (user.getActiveResearch() != null && user.getActiveResearch().equals(research)) {
            user.setActiveResearch(null);
            userRepository.save(user);
        }
        researchRepository.delete(research);
    }

    /**
     * Gets recommendations of potentially interesting papers for the research based on already added and hidden
     * papers
     *
     * @param research the research to get recommendations for
     * @return a list of recommended papers
     * @throws IOException when an error occurs getting the recommendations
     */
    public List<Paper> getRecommendations(Research research) throws IOException {
        List<Paper> positives = new ArrayList<>();
        List<Paper> negatives = new ArrayList<>();

        List<SavedPaper> savedPapers = savedPaperRepository.findBySavedPaperIdResearch(research);

        for (SavedPaper savedPaper : savedPapers) {
            switch (savedPaper.getSaveState()) {
                case ADDED -> positives.add(savedPaper.getSavedPaperId().getPaper());
                case HIDDEN -> negatives.add(savedPaper.getSavedPaperId().getPaper());
            }
        }
        return recommendationService.getRecommendations(positives, negatives);
    }

    /**
     * Finds all references from the list of papers. To optimize runtime the papers are already saved as
     * CachedPapers and only need to be found in the CachedPaperRepository
     *
     * @param research the research references are to be found for
     * @param papers   the papers whose references are to be found
     * @return a list of Cached Papers that hold the referenced papers
     */
    public List<CachedPaper> getReferences(Research research, List<Paper> papers) {
        return recommendationService.getCachedReferences(research, papers);
    }

    /**
     * Finds all citations from the list of papers. To optimize runtime the papers are already saved as
     * CachedPapers and only need to be found in the CachedPaperRepository
     *
     * @param research the research citations are to be found for
     * @param papers   the papers whose citations are to be found
     * @return a list of Cached Papers that hold the citing papers
     */
    public List<CachedPaper> getCitations(Research research, List<Paper> papers) {
        return recommendationService.getCachedCitations(research, papers);
    }

    /**
     * Finds papers based on a keyword
     *
     * @param keyword the keyword to search for
     * @return a list of papers that are related to the keyword
     * @throws IOException when there was an error getting the list of papers for this keyword
     */
    public List<Paper> searchByKeyword(String keyword) throws IOException {
        return apiActionController.getPapersByKeyword(keyword);
    }
}

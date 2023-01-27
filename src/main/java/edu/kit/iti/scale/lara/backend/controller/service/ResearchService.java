package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
import edu.kit.iti.scale.lara.backend.controller.repository.ResearchRepository;
import edu.kit.iti.scale.lara.backend.controller.repository.SavedPaperRepository;
import edu.kit.iti.scale.lara.backend.exceptions.NotInDataBaseException;
import edu.kit.iti.scale.lara.backend.exceptions.WrongUserException;
import edu.kit.iti.scale.lara.backend.model.research.Comment;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.cachedpaper.CachedPaper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import edu.kit.iti.scale.lara.backend.model.user.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchService {

    private final ResearchRepository researchRepository;
    private final ApiActionController apiActionController;
    private final RecommendationService recommendationService;
    private final SavedPaperRepository savedPaperRepository;

    public Research createResearch(User user, String title, String description) {
        UserCategory userCategory = new UserCategory("#0000ff", "User-Category");
        User user1 = new User("user", "password", userCategory);

        Research research = new Research(title, new Comment(description), ZonedDateTime.now(), user1);
        researchRepository.save(research);
        return research;
    }

    public Research getResearch(String researchId, User user) throws NotInDataBaseException, WrongUserException {
        if (researchRepository.findById(researchId).isPresent()) {
            Research research = researchRepository.findById(researchId).get();
            if (user.getResearches().contains(research)) {
                return research;
            } else {
                throw new WrongUserException();
            }
        } else {
            throw new NotInDataBaseException();
        }
    }

    public List<Research> getResearches(User user) {
        return researchRepository.findByUser(user);
    }

    public void updateResearch(Research research, String newTitle, String newDescription) {
        research.setTitle(newTitle);
        research.setDescription(new Comment(newDescription));
        researchRepository.save(research);
    }

    public void deleteResearch(Research research) {
        researchRepository.delete(research);
    }

    public List<Paper> getRecommendations(Research research) {
        List<Paper> positives = new ArrayList<>();
        List<Paper> negatives = new ArrayList<>();

        List<SavedPaper> savedPapers = savedPaperRepository.findByResearch(research);

        for (SavedPaper savedPaper : savedPapers) {
            switch (savedPaper.getSaveState()) {
                case ADDED -> positives.add(savedPaper.getPaper());
                case HIDDEN -> negatives.add(savedPaper.getPaper());
            }
        }
        return recommendationService.getRecommendations(positives, negatives);
    }

    public List<CachedPaper> getReferencesAndCitations(Research research, List<Paper> papers) { //Todo: split in two methods
        List<CachedPaper> referencesAndCitations = recommendationService.getReferences(research, papers);
        referencesAndCitations.addAll(recommendationService.getCitations(research, papers));
        return referencesAndCitations;
    }

    public List<Paper> searchByQuery(String query) {
        return apiActionController.getPapersByKeyword(query);
    }
}

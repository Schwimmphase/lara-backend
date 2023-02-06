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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchService {

    private final ResearchRepository researchRepository;
    private final ApiActionController apiActionController;
    private final RecommendationService recommendationService;
    private final SavedPaperRepository savedPaperRepository;
    private final UserRepository userRepository;

    public Research createResearch(User user, String title, String description) {
        Research research = new Research(title, new Comment(description), ZonedDateTime.now(), user);
        user.addResearch(research);
        userRepository.save(user);
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

    public List<CachedPaper> getReferences(Research research, List<Paper> papers) {
        return recommendationService.getReferences(research, papers);
    }

    public List<CachedPaper> getCitations(Research research, List<Paper> papers) {
        return recommendationService.getCitations(research, papers);
    }

    public List<Paper> searchByQuery(String query) throws IOException {
        return apiActionController.getPapersByKeyword(query);
    }
}

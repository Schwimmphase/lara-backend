package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import edu.kit.iti.scale.lara.backend.model.research.Research;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SavedPaper;
import edu.kit.iti.scale.lara.backend.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResearchService {

    public Research createResearch(User user, String title, String description) {

        // TODO

        return null;
    }

    public List<Research> getResearch(User user) {

        // TODO

        return null;
    }

    public void updateResearch(String researchId, User user, String newTitle, String newDescription) {

        // TODO

    }

    public void deleteResearch(String researchId, User user) {

        // TODO

    }

    public SavedPaper createSavePaper(String researchId, User user, Paper paper) {

        // TODO

        return null;
    }

    public List<Paper> getRecommendations(String researchId, User user, RecommendationMethod method) {

        // TODO

        return null;
    }

    public List<Paper> searchByQuery(String query) {

        // TODO

        return null;
    }
}

package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.controller.apicontroller.ApiActionController;
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

@Service
@RequiredArgsConstructor
public class PaperService {

    private final PaperRepository paperRepository;
    private final SavedPaperRepository savedPaperRepository;
    private final RecommendationService recommendationService;
    private final ApiActionController apiActionController;

    public void savePaperToDataBase(Paper paper) {
        paperRepository.save(paper);
    }

    public Paper getPaper(String id) throws NotInDataBaseException {
        return getPaper(id, false);
    }

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

    public SavedPaper createSavedPaper(Research research, Paper paper, SaveState saveState) throws IOException {
        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment(""), 0, saveState);
        savedPaperRepository.save(savedPaper);
        recommendationService.paperAdded(research, paper);
        return savedPaper;
        //savedPaper isn´t save in research.savedPapers to avoid duplicates

    }

    public void removeSavedPaper(Research research, SavedPaper savedPaper) {
        research.removeSavedPaper(savedPaper);
        savedPaperRepository.delete(savedPaper);
    }

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

    public List<SavedPaper> getSavedPapers(Research research, User user) throws WrongUserException {
        if (user.getActiveResearch().equals(research)) {
            return savedPaperRepository.findBySavedPaperIdResearch(research);
        } else {
            throw new WrongUserException();
        }
    }

    public void addTagToPaper(SavedPaper savedPaper, Tag tag) {
        savedPaper.getTags().add(tag);
        savedPaperRepository.save(savedPaper);
    }

    public void removeTagFromPaper(SavedPaper savedPaper, Tag tag) {
        savedPaper.getTags().remove(tag);
        savedPaperRepository.save(savedPaper);
    }

    public void commentPaper(SavedPaper savedPaper, String comment) {
        savedPaper.getComment().setText(comment);
        savedPaperRepository.save(savedPaper);
    }

    public void changeSaveState(SavedPaper savedPaper, SaveState saveState) throws IOException {
        savedPaper.setSaveState(saveState);
        savedPaperRepository.save(savedPaper);
        if (saveState.equals(SaveState.ADDED)) {
            recommendationService.paperAdded(savedPaper.getSavedPaperId().getResearch(), savedPaper.getPaper());
        }
        if (saveState.equals(SaveState.HIDDEN)) {
            recommendationService.paperRemoved(savedPaper.getSavedPaperId().getResearch(), savedPaper.getPaper());
        }
    }

    public void setRelevanceOfPaper(SavedPaper savedPaper, int relevance) {
        savedPaper.setRelevance(relevance);
    }
}

package edu.kit.iti.scale.lara.backend.controller.service;

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

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperService {

    private final PaperRepository paperRepository;
    private final SavedPaperRepository savedPaperRepository;
    private final RecommendationService recommendationService;

    public void savePaperToDataBase(Paper paper) {
        paperRepository.save(paper);
    }

    public Paper getPaper(String id) throws NotInDataBaseException {
        if (paperRepository.findById(id).isPresent()) {
            return paperRepository.findById(id).get();
        } else {
            throw new NotInDataBaseException();
        }

    }

    public SavedPaper createSavedPaper(Research research, Paper paper, SaveState saveState) {
        SavedPaper savedPaper = new SavedPaper(paper, research, new Comment(""), 0, saveState);
        research.addSavedPaper(savedPaper);
        savedPaperRepository.save(savedPaper);
        return savedPaper;
    }

    public SavedPaper getSavedPaper(SavedPaper.SavedPaperId id, User user) throws WrongUserException, NotInDataBaseException {
        if (savedPaperRepository.findById(id).isPresent()) {
            SavedPaper savedPaper = savedPaperRepository.findById(id).get();
            if (user.getResearches().contains(savedPaper.getResearch())) {
                return savedPaper;
            } else {
                throw new WrongUserException();
            }
        } else throw new NotInDataBaseException();
    }

    public List<SavedPaper> getSavedPapers(Research research, User user) throws WrongUserException {
        if (user.getResearches().contains(research)) {
            return savedPaperRepository.findByResearch(research);
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

    public void changeSaveState(SavedPaper savedPaper, SaveState saveState) {
        savedPaper.setSaveState(saveState);
        savedPaperRepository.save(savedPaper);
        if (saveState == SaveState.ADDED) {
            recommendationService.paperAdded(savedPaper.getResearch(), savedPaper.getPaper());
        }
    }

    public void setRelevanceOfPaper(SavedPaper savedPaper, int relevance) {
        savedPaper.setRelevance(relevance);
    }
}

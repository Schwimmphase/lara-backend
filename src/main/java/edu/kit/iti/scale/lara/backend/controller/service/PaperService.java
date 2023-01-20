package edu.kit.iti.scale.lara.backend.controller.service;

import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class PaperService {

    public void savePaperToDataBase(String id, String title, int year, String abstractText, int citationCount, int referenceCount, String venue, URL pdfUrl) {

        // TODO

    }

    public Paper getPaper(String id) {

        // TODO

        return null;
    }

    public SavedPaper getPaper(String id, User user) {

        // TODO

        return null;
    }

    public List<Paper> getPapers(Research research, User user) {

        // TODO

        return null;
    }

    public void addTagToPaper(String id, User user, String tagId) {

        // TODO

    }

    public void removeTagFromPaper(String id, User user, String tagId) {

        // TODO

    }

    public void commentPaper(String id, User user, String comment) {

        // TODO

    }

    public void changeSaveState(String id, User user, SaveState saveState) {

        // TODO

    }

    public void setRelevanceOfPaper(String id, User user, int relevance) {

        // TODO

    }
}

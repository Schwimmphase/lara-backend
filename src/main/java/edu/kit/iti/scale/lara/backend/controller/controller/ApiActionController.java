package edu.kit.iti.scale.lara.backend.controller.controller;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApiActionController {

    public Paper getPaper(String paperId) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper = new Paper(paperId, "thePaper", 2023, "abstract",
                0, 0,"venue", "url", author);

        return paper;
    }

    public List<Paper> getPapersByKeyword(String query) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "KeyPaper1", 2023, "abstract1",
                1, 1,"venue1", "url1", author);
        Paper paper2 = new Paper("222222", "KeyPaper2", 2023, "abstract2",
                2, 2,"venue2", "url2", author);
        Paper paper3 = new Paper("333333", "KeyPaper3", 2023, "abstract3",
                3, 3,"venue3", "url3", author);

        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return papers;
    }

    public List<Paper> getRecommendations(List<Paper> positives, List<Paper> negatives) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "recPaper1", 2023, "abstract1",
                1, 1,"venue1", "url1", author);
        Paper paper2 = new Paper("222222", "recPaper2", 2023, "abstract2",
                2, 2,"venue2", "url2", author);
        Paper paper3 = new Paper("333333", "recPaper3", 2023, "abstract3",
                3, 3,"venue3", "url3", author);

        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return papers;
    }

    public List<Paper> getCitations(Paper paper) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "citPaper1", 2023, "abstract1",
                1, 1,"venue1", "url1", author);
        Paper paper2 = new Paper("222222", "citPaper2", 2023, "abstract2",
                2, 2,"venue2", "url2", author);
        Paper paper3 = new Paper("333333", "citPaper3", 2023, "abstract3",
                3, 3,"venue3", "url3", author);

        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return papers;
    }

    public List<Paper> getReferences(Paper paper) {

        //mock
        Author author = new Author("mockId", "mockName");
        Paper paper1 = new Paper("111111", "refPaper1", 2023, "abstract1",
                1, 1,"venue1", "url1", author);
        Paper paper2 = new Paper("222222", "refPaper2", 2023, "abstract2",
                2, 2,"venue2", "url2", author);
        Paper paper3 = new Paper("333333", "refPaper3", 2023, "abstract3",
                3, 3,"venue3", "url3", author);

        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);

        return papers;
    }
}

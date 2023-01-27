package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BibtexConverterService {

    public String export(List<Paper> papers) {
        StringBuilder bibTexBuilder = new StringBuilder();
        for (Paper paper : papers) {
            bibTexBuilder.append(paperToBibTex(paper));
            bibTexBuilder.append("\n");
        }
        return bibTexBuilder.toString();
    }

    private String paperToBibTex(Paper paper) {
        //todo
        return "@article{Test," +
                "title={" + paper.getTitle() + "}," +
                getAuthorsAsBibTex(paper.getAuthors()) +
                "year={" + paper.getYear() + "}," +
                "journal={" + paper.getVenue() + "}";
    }

    private String getAuthorsAsBibTex(List<Author> authors) {
        StringBuilder authorsAsBibTexBuilder = new StringBuilder();
        authorsAsBibTexBuilder.append("author={");
        boolean first = true;
        for (Author author : authors) {
            if (!first) {
                authorsAsBibTexBuilder.append(" and ");
            }
            first = false;
            authorsAsBibTexBuilder.append(author.getName());
        }
        authorsAsBibTexBuilder.replace(0, 4, "");
        return authorsAsBibTexBuilder.toString();
    }
}


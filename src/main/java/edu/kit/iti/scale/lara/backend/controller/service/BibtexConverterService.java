package edu.kit.iti.scale.lara.backend.controller.service;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class converts a list of papers to a String in bibtex-format, so it can be included in a .bib document
 *
 * @author ukgcc
 */
@Service
public class BibtexConverterService {

    public String export(List<Paper> papers) {
        StringBuilder bibTexBuilder = new StringBuilder();
        for (Paper paper : papers) {
            bibTexBuilder.append(paperToBibTex(paper));
            bibTexBuilder.append("\n\n");
        }
        return bibTexBuilder.toString();
    }

    private String paperToBibTex(Paper paper) {
        return "@article{" + getIdentifier(paper) + ", " +
                "title={" + paper.getTitle() + "}, " +
                getAuthorsAsBibTex(paper.getAuthors()) + ", " +
                "year={" + paper.getYearPublished() + "}, " +
                "journal={" + paper.getVenue() + "}" +
                "}";
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
        authorsAsBibTexBuilder.append("}");
        return authorsAsBibTexBuilder.toString();
    }

    private String getIdentifier(Paper paper) {
        StringBuilder identifierBuilder = new StringBuilder();
        identifierBuilder.append(paper.getAuthors().get(0).getName(), 0, 2);
        identifierBuilder.append(paper.getYearPublished());
        identifierBuilder.append(paper.getTitle()
                        .replace(" ", ""),
                0, 3);
        return identifierBuilder.toString();
    }
}


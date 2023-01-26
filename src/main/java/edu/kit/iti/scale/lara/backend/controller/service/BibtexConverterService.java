package edu.kit.iti.scale.lara.backend.controller.service;

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
                "title={" + paper.getTitle() + "}" +
                "author={" + paper.getAuthor().getName() + "}" +
                "year={" + paper.getYear() + "}";
    }
}


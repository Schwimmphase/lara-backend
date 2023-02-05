package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.springframework.security.core.parameters.P;

import java.util.Comparator;
import java.util.List;

public class CitationCountSorter extends Sorter {

    @Override
    public List<Paper> organize(List<Paper> papers) {
        papers.sort(Comparator.comparing(Paper::getCitationCount));
        return papers;
    }
}

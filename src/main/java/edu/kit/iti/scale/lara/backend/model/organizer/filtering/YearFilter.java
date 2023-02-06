package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.ArrayList;
import java.util.List;

@NamedOrganizer("year-filter")
public class YearFilter<T extends Paper> implements Filter<T> {

    private final int min;
    private final int max;

    public YearFilter(String argument) {
        if (argument == null || argument.matches("[0-9]+-[0-9]+")) {
            throw new IllegalArgumentException("Argument must not be null or not match the pattern [0-9]+-[0-9]+");
        }

        String[] split = argument.split("-");
        min = Integer.parseInt(split[0]);
        max = Integer.parseInt(split[1]);

        if (min > max) {
            throw new IllegalArgumentException("Min must be smaller than max");
        }
    }

    @Override
    public List<T> organize(List<T> papers) {
        papers = new ArrayList<>(papers);
        papers.removeIf(paper -> paper.getYearPublished() < min || paper.getYearPublished() > max);
        return papers;
    }
}

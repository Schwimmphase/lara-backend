package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.ArrayList;
import java.util.List;

@NamedOrganizer("venue-filter")
public class VenueFilter<T extends Paper> implements Filter<T> {

    private final String[] venues;

    public VenueFilter(String argument) {
        if (argument == null || argument.isEmpty()) {
            throw new IllegalArgumentException("Argument must not be null or empty");
        }
        venues = argument.split(",");
    }

    @Override
    public List<T> organize(List<T> papers) {
        List<T> organizedPapers = new ArrayList<>();
        for (String venue : venues) {
            for (T paper : papers) {
                if (paper.getVenue().contains(venue)) {
                    organizedPapers.add(paper);
                }
            }
        }
        return organizedPapers;
    }

}

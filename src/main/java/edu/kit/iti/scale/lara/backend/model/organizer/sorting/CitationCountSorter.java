package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

@NamedOrganizer("CitationCountSorter")
public class CitationCountSorter<T extends Paper> extends Sorter<T> {

    public CitationCountSorter(OrganizerRequest request) {
        super(request);
    }

    @Override
    public List<T> organize(List<T> papers) {
        //todo
        return null;
    }
}

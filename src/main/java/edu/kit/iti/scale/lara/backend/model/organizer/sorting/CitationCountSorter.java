package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

@NamedOrganizer("CitationCountSorter")
public class CitationCountSorter extends Sorter {

    public CitationCountSorter(OrganizerRequest request) {
        super(request);
    }

    @Override
    public List<Paper> organize(List<Paper> papers) {
        //todo
        return null;
    }
}

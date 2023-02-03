package edu.kit.iti.scale.lara.backend.model.organizer.sorting;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.organizer.NamedOrganizer;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

@NamedOrganizer("YearSorter")
public class YearSorter extends Sorter {

    public YearSorter(OrganizerRequest request) {
        super(request);
    }

    @Override
    public List<Paper> organize(List<Paper> papers) {
        //todo
        return null;
    }
}

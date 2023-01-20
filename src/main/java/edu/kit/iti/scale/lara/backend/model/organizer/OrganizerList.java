package edu.kit.iti.scale.lara.backend.model.organizer;

import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;

import java.util.List;

public class OrganizerList {

    private List<Organizer> organizers;

    public List<Paper> organize(List<Paper> papers) {
        //todo
        return null;
    }

    public boolean add(Organizer organizer) {
        return organizers.add(organizer);
    }

    public boolean remove(Organizer organizer) {
        return organizers.remove(organizer);
    }

    public List<Organizer> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Organizer> organizers) {
        this.organizers = organizers;
    }

}

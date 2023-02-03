package edu.kit.iti.scale.lara.backend.model.organizer;

import java.util.List;

public interface Organizer<T> {

    List<T> organize(List<T> elements);

}

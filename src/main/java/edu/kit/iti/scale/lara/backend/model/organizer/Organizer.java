package edu.kit.iti.scale.lara.backend.model.organizer;

import java.util.List;

/**
 * This interface represents an organizer.
 * @param <T>
 *
 * @author ukgcc, unqkm
 */
public interface Organizer<T> {

    List<T> organize(List<T> elements);

}

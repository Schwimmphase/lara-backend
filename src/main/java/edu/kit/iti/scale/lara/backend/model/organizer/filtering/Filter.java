package edu.kit.iti.scale.lara.backend.model.organizer.filtering;

import edu.kit.iti.scale.lara.backend.model.organizer.Organizer;

/**
 * A filter is a special organizer that can be used to filter a list of objects.
 * @param <T> the type of the objects that can be filtered
 *
 * @author ukgcc, unqkm
 */
public interface Filter<T> extends Organizer<T> {
}

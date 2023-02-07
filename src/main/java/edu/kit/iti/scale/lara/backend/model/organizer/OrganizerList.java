package edu.kit.iti.scale.lara.backend.model.organizer;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrganizerList<T> {

    private List<Organizer<T>> organizers = new ArrayList<>();

    public List<T> organize(List<T> elements) {
        List<T> organizedElements = new ArrayList<>(elements);
        for (Organizer<T> organizer : organizers) {
            organizedElements = organizer.organize(organizedElements);
        }
        return organizedElements;
    }

    public boolean add(Organizer<T> organizer) {
        return organizers.add(organizer);
    }

    public boolean remove(Organizer<T> organizer) {
        return organizers.remove(organizer);
    }

    public List<Organizer<T>> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Organizer<T>> organizers) {
        this.organizers = organizers;
    }

    public static <T> OrganizerList<T> createFromOrganizerRequests(List<OrganizerRequest> requests)
            throws IllegalArgumentException, IllegalStateException {
        OrganizerList<T> organizerList = new OrganizerList<>();

        for (OrganizerRequest request : requests) {
            organizerList.add(getOrganizerByName(request.name(), request.argument()));
        }

        return organizerList;
    }

    private static <T> Organizer<T> getOrganizerByName(String name, String arguments) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.TypesAnnotated));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(NamedOrganizer.class);

        for (Class<?> clazz : classes) {
            String className = clazz.getAnnotation(NamedOrganizer.class).value();

            if (!className.equals(name)) {
                continue;
            }

            try {
                return (Organizer<T>) clazz.getDeclaredConstructor(String.class).newInstance(arguments);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new IllegalStateException(String.format("Organizer '%s' with no string constructor", className), e);
            }
        }

        throw new IllegalArgumentException("No organizer with this name exists");
    }


}

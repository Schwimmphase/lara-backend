package edu.kit.iti.scale.lara.backend.model.organizer;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import edu.kit.iti.scale.lara.backend.model.research.paper.Paper;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

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

    public static OrganizerList createFromOrganizerRequests(List<OrganizerRequest> requests)
            throws IllegalArgumentException, IllegalStateException {
        OrganizerList organizerList = new OrganizerList();

        for (OrganizerRequest request : requests) {
            organizerList.add(getOrganizerByName(request.name(), request.argument()));
        }

        return organizerList;
    }

    private static Organizer getOrganizerByName(String name, String arguments) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath()).setScanners(Scanners.TypesAnnotated));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(NamedOrganizer.class);

        for (Class<?> clazz : classes) {
            if (!clazz.isInstance(Organizer.class) || !clazz.getAnnotation(NamedOrganizer.class).value().equals(name)) {
                continue;
            }

            try {
                return (Organizer) clazz.getDeclaredConstructor(String.class).newInstance(arguments);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new IllegalStateException("Organizer with now string constructor");
            }
        }

        throw new IllegalArgumentException("No organizer with this name exists");
    }


}

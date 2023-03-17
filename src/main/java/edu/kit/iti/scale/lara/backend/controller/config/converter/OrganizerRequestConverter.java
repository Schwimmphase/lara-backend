package edu.kit.iti.scale.lara.backend.controller.config.converter;

import edu.kit.iti.scale.lara.backend.controller.request.OrganizerRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrganizerRequestConverter implements Converter<String, OrganizerRequest> {

    private static final String SEPARATOR = "=";

    @Override
    public OrganizerRequest convert(@NotNull String source) {
        String[] split = source.split(SEPARATOR);
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid organizer request: " + source);
        }
        return new OrganizerRequest(split[0], split[1]);
    }

}

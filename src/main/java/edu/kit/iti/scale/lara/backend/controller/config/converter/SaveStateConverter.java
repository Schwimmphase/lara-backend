package edu.kit.iti.scale.lara.backend.controller.config.converter;

import edu.kit.iti.scale.lara.backend.model.research.paper.savedpaper.SaveState;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SaveStateConverter implements Converter<String, SaveState> {

    @Override
    public SaveState convert(@NotNull String source) {
        if (Arrays.stream(SaveState.values()).noneMatch(saveState -> saveState.name().equals(source.toUpperCase()))) {
            throw new IllegalArgumentException("Invalid save state: " + source.toUpperCase());
        }

        return SaveState.valueOf(source.toUpperCase());
    }

}

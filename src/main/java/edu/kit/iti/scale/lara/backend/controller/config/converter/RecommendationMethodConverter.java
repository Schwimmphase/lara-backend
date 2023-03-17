package edu.kit.iti.scale.lara.backend.controller.config.converter;

import edu.kit.iti.scale.lara.backend.controller.RecommendationMethod;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class RecommendationMethodConverter implements Converter<String, RecommendationMethod> {
    @Override
    public RecommendationMethod convert(@NotNull String source) {
        if (Arrays.stream(RecommendationMethod.values())
                .noneMatch(recommendationMethod -> recommendationMethod.name().equals(source.toUpperCase()))) {
            throw new IllegalArgumentException("Invalid recommendation method: " + source.toUpperCase());
        }
        return RecommendationMethod.valueOf(source.toUpperCase());
    }
}

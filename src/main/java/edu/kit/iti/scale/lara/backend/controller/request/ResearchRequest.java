package edu.kit.iti.scale.lara.backend.controller.request;

import org.jetbrains.annotations.NotNull;

public record ResearchRequest(@NotNull String title, @NotNull String description) {
}

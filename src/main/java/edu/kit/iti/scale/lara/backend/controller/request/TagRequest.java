package edu.kit.iti.scale.lara.backend.controller.request;

import org.jetbrains.annotations.NotNull;

public record TagRequest(@NotNull String name, @NotNull String color) {
}

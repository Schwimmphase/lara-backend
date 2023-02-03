package edu.kit.iti.scale.lara.backend.controller.request;

import org.jetbrains.annotations.NotNull;

public record OrganizerRequest(@NotNull String name, @NotNull String argument) {
}

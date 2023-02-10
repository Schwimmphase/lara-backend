package edu.kit.iti.scale.lara.backend.controller.request;

import org.jetbrains.annotations.NotNull;

public record LoginRequest(@NotNull String username, @NotNull String password) {
}

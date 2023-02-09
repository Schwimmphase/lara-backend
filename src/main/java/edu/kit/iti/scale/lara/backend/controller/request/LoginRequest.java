package edu.kit.iti.scale.lara.backend.controller.request;

import org.jetbrains.annotations.NotNull;

// TODO: userId -> username wait for frontend
public record LoginRequest(@NotNull String userId, @NotNull String password) {
}

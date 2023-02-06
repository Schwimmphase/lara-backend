package edu.kit.iti.scale.lara.backend.controller.request;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record UserRequest(@NotNull String username, @Nullable String password, @NotNull String usercategory) {
}

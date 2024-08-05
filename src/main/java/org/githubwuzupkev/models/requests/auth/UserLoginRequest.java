package org.githubwuzupkev.models.requests.auth;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank String username,
                               @NotBlank String password) {
}

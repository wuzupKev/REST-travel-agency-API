package org.githubwuzupkev.models.responses.auth;

public record UserResponse(String username,
                           String message,
                           String jwt,
                           Boolean status) {
}

package com.psychology.product.repository.dto;

import java.time.Instant;
import java.util.UUID;

public record TokenDTO(
        UUID id,
        String token,
        boolean revoked,
        boolean expired,
        UserDTO user,
        Instant updatedTimestamp
) {
    public TokenDTO(String token, UserDTO user) {
        this(null, token, false, false, user, Instant.now());
    }
}

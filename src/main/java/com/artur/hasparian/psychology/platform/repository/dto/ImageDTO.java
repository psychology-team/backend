package com.artur.hasparian.psychology.platform.repository.dto;

import java.time.Instant;
import java.util.UUID;

public record ImageDTO(
        UUID id,
        String name,
        Long size,
        String contentType,
        Instant createdTimestamp
) {
}

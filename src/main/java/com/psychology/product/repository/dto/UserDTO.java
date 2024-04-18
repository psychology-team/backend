package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;

import java.util.UUID;

public record UserDTO(
        @JsonView(JsonViews.UUID.class)
        UUID id,
        @JsonView(JsonViews.FirstName.class)
        String firstName,
        @JsonView(JsonViews.LastName.class)
        String lastName,
        @JsonView(JsonViews.Email.class)
        String email,
        @JsonView(JsonViews.Phone.class)
        String phone,
        Boolean enabled,
        Boolean revoked) {
}
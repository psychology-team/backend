package com.psychology.product.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull
                           @Email(message = "Please provide a valid email address.") String email,
                           @NotNull
                           String password) {
}

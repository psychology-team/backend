package com.psychology.product.controller.request;

import jakarta.validation.constraints.NotNull;

public record ForgotPasswordRequest(@NotNull String email) {
}

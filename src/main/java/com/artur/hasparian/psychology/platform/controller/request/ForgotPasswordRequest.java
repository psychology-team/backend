package com.artur.hasparian.psychology.platform.controller.request;

import jakarta.validation.constraints.NotNull;

public record ForgotPasswordRequest(@NotNull String email) {
}

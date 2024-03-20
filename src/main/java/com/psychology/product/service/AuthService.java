package com.psychology.product.service;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.LoginResponse;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
    LoginResponse loginUser(LoginRequest loginRequest);

    LoginResponse getJwtAccessToken(@NotNull String refreshToken) throws AuthException;

    void saveJwtRefreshToken(String email, String jwtRefreshToken);
}

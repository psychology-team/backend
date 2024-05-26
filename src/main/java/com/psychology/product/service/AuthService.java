package com.psychology.product.service;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.JwtResponse;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
    JwtResponse loginUser(LoginRequest loginRequest);

    JwtResponse getJwtAccessToken(@NotNull String refreshToken) throws AuthException;

    JwtResponse getJwtRefreshToken(String refreshToken) throws AuthException;

    void logout();
}

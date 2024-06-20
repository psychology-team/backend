package com.psychology.product.service;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.JwtResponse;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
    /**
     * Authenticates a user and returns a JWT response containing the access and refresh tokens.
     *
     * @param loginRequest the request object containing login credentials
     * @return a JwtResponse containing the JWT access and refresh tokens
     */
    JwtResponse loginUser(LoginRequest loginRequest);

    /**
     * Generates a new JWT access token using the provided refresh token.
     *
     * @param refreshToken the refresh token used to obtain a new access token
     * @return a JwtResponse containing the new JWT access token
     * @throws AuthException if the refresh token is invalid or expired
     */
    JwtResponse getJwtAccessToken(@NotNull String refreshToken) throws AuthException;

    /**
     * Generates a new JWT refresh token using the provided refresh token.
     *
     * @param refreshToken the refresh token used to obtain a new refresh token
     * @return a JwtResponse containing the new JWT refresh token
     * @throws AuthException if the refresh token is invalid or expired
     */
    JwtResponse getJwtRefreshToken(String refreshToken) throws AuthException;

    /**
     * Logs out the current user and invalidates their session.
     */
    void logout();
}

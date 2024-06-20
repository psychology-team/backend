package com.psychology.product.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.security.core.Authentication;

import java.security.Key;

public interface JwtUtils {

    /**
     * Generates a JWT access token for the given authentication object.
     *
     * @param authentication the authentication object containing user details
     * @return the generated JWT access token
     */
    String generateJwtToken(@NonNull Authentication authentication);

    /**
     * Generates a JWT refresh token for the given authentication object.
     *
     * @param authentication the authentication object containing user details
     * @return the generated JWT refresh token
     */
    String generateRefreshToken(@NonNull Authentication authentication);

    /**
     * Extracts the email address from the given JWT token.
     *
     * @param token the JWT token
     * @return the email address extracted from the token
     */
    String getEmailFromJwtToken(String token);

    /**
     * Validates the given JWT access token.
     *
     * @param token the JWT access token to validate
     * @return true if the token is valid, false otherwise
     */
    boolean validateJwtAccessToken(@NonNull String token);

    /**
     * Validates the given JWT refresh token.
     *
     * @param token the JWT refresh token to validate
     * @return true if the token is valid, false otherwise
     */
    boolean validateJwtRefreshToken(@NonNull String token);

    /**
     * Extracts claims from the given JWT refresh token.
     *
     * @param token the JWT refresh token
     * @return the claims extracted from the token
     */
    Claims getRefreshClaims(@NonNull String token);

    /**
     * Validates the given JWT token with the provided secret key.
     *
     * @param token the JWT token to validate
     * @param secret the secret key used to validate the token
     * @return true if the token is valid, false otherwise
     */
    boolean validateJwtToken(@NonNull String token, @NonNull Key secret);
}

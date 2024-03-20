package com.psychology.product.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import org.springframework.security.core.Authentication;

import java.security.Key;

public interface JwtUtils {

    String generateJwtToken(@NonNull Authentication authentication);

    String generateRefreshToken(@NonNull Authentication authentication);

    String getEmailFromJwtToken(String token);

    boolean validateJwtAccessToken(@NonNull String token);

    boolean validateJwtRefreshToken(@NonNull String token);

    Claims getRefreshClaims(@NonNull String token);

    boolean validateJwtToken(@NonNull String token, @NonNull Key secret);
}

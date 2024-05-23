package com.psychology.product.service;

import com.psychology.product.repository.dto.TokenDTO;
import com.psychology.product.util.Tokens;
import org.springframework.security.core.Authentication;

public interface TokenService {
    void revokedToken(TokenDTO tokenDTO);

    boolean isTokenExpired(TokenDTO tokenDTO);

    boolean isTokenRevoked(TokenDTO tokenDTO);

    Tokens recordTokens(Authentication authentication);
}

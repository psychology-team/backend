package com.psychology.product.config;

import com.psychology.product.repository.TokenRepository;
import com.psychology.product.repository.model.Token;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/login") && request.getServletPath().contains("/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.substring(7);

        Token tokenDAO = tokenRepository.findByToken(token);

        Optional.ofNullable(tokenDAO).ifPresent(tokenDAOPresent -> {
            if (tokenDAOPresent.isExpired() || tokenDAOPresent.isRevoked()) {
                throw new InsufficientAuthenticationException("Token is revoked or expired");
            }
        });

        filterChain.doFilter(request, response);
    }
}

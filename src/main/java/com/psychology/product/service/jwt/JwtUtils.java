package com.psychology.product.service.jwt;

import com.psychology.product.service.impl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret.access.expirationMs}")
    private long jwtAccessExpirationMs;
    @Value("${jwt.secret.refresh.expirationMs}")
    private long jwtRefreshExpirationMs;

    private final Key jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtUtils(
            @Value(("${jwt.secret.access}")) String jwtAccessSecret,
            @Value(("${jwt.secret.refresh}")) String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateJwtToken(@NonNull Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        final Date expiration = new Date(new Date().getTime() + jwtAccessExpirationMs);

        return Jwts.builder()
                .claim("id", userPrincipal.getUser().getId())
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(@NonNull Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        final Date expiration = new Date(new Date().getTime() + jwtRefreshExpirationMs);
        return Jwts.builder()
                .subject((userPrincipal.getUsername()))
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) jwtAccessSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtAccessToken(@NonNull String token) {
        return validateJwtToken(token, jwtAccessSecret);
    }

    public boolean validateJwtRefreshToken(@NonNull String token) {
        return validateJwtToken(token, jwtRefreshSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    public boolean validateJwtToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parser().verifyWith((SecretKey) secret).build().parse(token);
            return true;
        } catch (Exception e) {
            if (e instanceof ExpiredJwtException) return false;
            log.error("Failed to validate JWT token", e);
        }

        return false;
    }

    private Claims getClaims(@NonNull String token, @NonNull Key jwtRefreshSecret) {
        return Jwts.parser()
                .verifyWith((SecretKey) jwtRefreshSecret)
                .decryptWith((SecretKey) jwtRefreshSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

package com.psychology.product.service.impl;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.JwtResponse;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.User;
import com.psychology.product.service.AuthService;
import com.psychology.product.service.JwtUtils;
import com.psychology.product.service.TokenService;
import com.psychology.product.service.UserService;
import com.psychology.product.util.Tokens;
import com.psychology.product.util.exception.ForbiddenException;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Tokens generatedTokens = tokenService.generateNewTokens(authentication);
        return new JwtResponse(generatedTokens.accessToken(), generatedTokens.refreshToken());
    }

    @Override
    public JwtResponse getJwtAccessToken(String refreshToken) throws AuthException {
        if (jwtUtils.validateJwtRefreshToken(refreshToken)) {
            Authentication authentication = authenticateWithRefreshToken(refreshToken);
            Tokens tokens = tokenService.generateAccessToken(authentication, refreshToken);
            return new JwtResponse(tokens.accessToken(), refreshToken);
        }
        throw new AuthException("Invalid token");
    }

    @Override
    public JwtResponse getJwtRefreshToken(String refreshToken) throws AuthException {
        if (jwtUtils.validateJwtRefreshToken(refreshToken)) {
            Authentication authentication = authenticateWithRefreshToken(refreshToken);
            Tokens tokens = tokenService.generateNewTokens(authentication);
            return new JwtResponse(tokens.accessToken(), tokens.refreshToken());
        }
        throw new AuthException("Invalid token");
    }

    @Override
    public void logout() {
        UserDTO userDTO = userService.getCurrentUser();
        tokenService.logout(userDTO);
    }


    private Authentication authenticateWithRefreshToken(String refreshToken) {
        if (!jwtUtils.validateJwtRefreshToken(refreshToken)) {
            throw new ForbiddenException("Invalid refresh token.");
        }
        Claims claims = jwtUtils.getRefreshClaims(refreshToken);
        String login = claims.getSubject();
        User user = new User();
        user.setEmail(login);
        Authentication authentication = userService.userAuthentication(user);
        if (authentication == null) {
            throw new ForbiddenException("Not allowed.");
        }
        return authentication;
    }

}

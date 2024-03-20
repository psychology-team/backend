package com.psychology.product.service.impl;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.JwtResponse;
import com.psychology.product.controller.response.LoginResponse;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.service.AuthService;
import com.psychology.product.service.UserService;
import com.psychology.product.service.jwt.JwtUtils;
import com.psychology.product.util.exception.ForbiddenException;
import com.psychology.product.util.exception.NotFoundException;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        if (!checkExistUser(loginRequest.email()))
            throw new NotFoundException("User not found");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        return new LoginResponse(jwtToken, refreshToken);

    }

    @Override
    public JwtResponse getJwtAccessToken(String refreshToken) throws AuthException {
        if (jwtUtils.validateJwtRefreshToken(refreshToken)) {
            Authentication authentication = authenticateWithRefreshToken(refreshToken);
            String accessToken = jwtUtils.generateJwtToken(authentication);
            return new JwtResponse(accessToken, null);
        }
        throw new AuthException("Invalid token");
    }


    private Authentication authenticateWithRefreshToken(String refreshToken) {
        if (!jwtUtils.validateJwtRefreshToken(refreshToken)) {
            throw new ForbiddenException("Invalid refresh token.");
        }
        Claims claims = jwtUtils.getRefreshClaims(refreshToken);
        String login = claims.getSubject();
        String saveRefreshToken = refreshStorage.get(login);
        if (saveRefreshToken == null || !saveRefreshToken.equals(refreshToken)) {
            throw new ForbiddenException("Invalid refresh token.");
        }
        UserDAO user = new UserDAO();
        user.setEmail(login);
        Authentication authentication = userService.userAuthentication(user);
        if (authentication == null) {
            throw new ForbiddenException("Not allowed.");
        }
        return authentication;
    }

    private boolean checkExistUser(String email) {
        return userService.findUserByEmail(email);
    }


}

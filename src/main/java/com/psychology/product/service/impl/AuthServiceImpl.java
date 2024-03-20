package com.psychology.product.service.impl;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.LoginResponse;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.service.AuthService;
import com.psychology.product.service.jwt.JwtUtils;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        if (!checkExistUser(loginRequest.email()))
            throw new NotFoundException("User not found");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        return new LoginResponse(jwtToken, refreshToken);

    }

    private boolean checkExistUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

package com.psychology.product.service.impl;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.controller.response.LoginResponse;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.model.UserAuthority;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.service.AuthService;
import com.psychology.product.service.UserService;
import com.psychology.product.service.jwt.JwtUtils;
import com.psychology.product.util.exception.ConflictException;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public void createNewUser(SignUpRequest signUpRequest) {

        if (checkExistUser(signUpRequest.email())) throw new ConflictException("User already exist");

        UserDAO user = new UserDAO();
        user.setFirstName(signUpRequest.firstName());
        user.setLastName((signUpRequest.lastName()));
        user.setEmail((signUpRequest.email()));
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setAuthorities((Set.of(UserAuthority.USER)));
        user.setCreatedTimestamp(Instant.now());
        user.setEnabled(true);

        userRepository.save(user);

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        if (!checkExistUser(loginRequest.email()))
            throw new NotFoundException(String.format("Not found user with email: %s", loginRequest.email()));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        return new LoginResponse(jwtToken, refreshToken);

    }

    private boolean checkExistUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}

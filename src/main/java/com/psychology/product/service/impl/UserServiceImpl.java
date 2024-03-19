package com.psychology.product.service.impl;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.UserRepository;
import com.psychology.product.repository.model.UserAuthority;
import com.psychology.product.repository.model.UserDAO;
import com.psychology.product.service.UserService;
import com.psychology.product.util.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    private boolean checkExistUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}

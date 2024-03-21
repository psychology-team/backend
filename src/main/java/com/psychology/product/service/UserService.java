package com.psychology.product.service;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.model.UserDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface UserService {
    void createNewUser(SignUpRequest signUpRequest);

    boolean findUserByEmail(String email);

    Authentication userAuthentication(UserDAO user);
    UserDAO findUserById(UUID userId);
    ResponseEntity<?> deleteUser(String userId);
}

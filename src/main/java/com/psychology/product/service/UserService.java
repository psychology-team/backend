package com.psychology.product.service;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.model.UserDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {
    void createNewUser(SignUpRequest signUpRequest);

    Optional<UserDAO> findUserByEmail(String email);

    Authentication userAuthentication(UserDAO user);
    ResponseEntity<?> deleteUser();
}

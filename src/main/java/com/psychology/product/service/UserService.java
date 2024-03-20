package com.psychology.product.service;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.repository.model.UserDAO;
import org.springframework.security.core.Authentication;

public interface UserService {
    void createNewUser(SignUpRequest signUpRequest);

    boolean findUserByEmail(String email);

    Authentication userAuthentication(UserDAO user);
}

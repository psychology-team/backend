package com.psychology.product.service;

import com.psychology.product.controller.request.SignUpRequest;

public interface UserService {
    void createNewUser(SignUpRequest signUpRequest);
}

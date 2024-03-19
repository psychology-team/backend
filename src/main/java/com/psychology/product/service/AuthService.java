package com.psychology.product.service;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}

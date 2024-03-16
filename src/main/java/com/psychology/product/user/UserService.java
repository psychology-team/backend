package com.psychology.product.user;

import com.psychology.product.auth.SignUpRequest;

public interface UserService {
    void createNewUser(SignUpRequest signUpRequest);
}

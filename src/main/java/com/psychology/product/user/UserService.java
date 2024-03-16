package com.psychology.product.user;

import com.psychology.product.auth.UserSignUp;

public interface UserService {
    void createNewUser(UserSignUp userSignUp);
}

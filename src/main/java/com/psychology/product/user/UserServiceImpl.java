package com.psychology.product.user;

import com.psychology.product.auth.UserSignUp;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public void createNewUser(UserSignUp userSignUp) {
        System.out.println(userSignUp.getFirstName());
    }
}

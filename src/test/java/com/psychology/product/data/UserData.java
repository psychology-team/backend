package com.psychology.product.data;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Value;

public class UserData {

    @Value("$jwt.secret.access")
    public static String jwtAccessToken;

    @Value("$jwt.secret.refresh")
    public static String jwtRefreshToken;

    public static SignUpRequest getValidSignUpRequest() {
        return new SignUpRequest(getValidFirstName(), getValidLastName(), getValidEmail(), getValidPassword(), getValidPhoneNumber());
    }

    public static LoginRequest getValidLoginRequest() {
        return new LoginRequest(getValidEmail(), getValidPassword());
    }

    public static String getValidFirstName() {
        return "Alex";
    }

    public static String getValidLastName() {
        return "Developerov";
    }

    public static String getValidEmail() {
        return "test@gmail.com";
    }

    public static String getInvalidEmail() {
        return "emailwithout.dog";
    }

    public static String getValidPhoneNumber() {
        return "+380973334455";
    }

    public static String getInvalidPhoneNumber() {
        return "347894738";
    }

    public static String getValidPassword() {
        return "Qwerty123!";
    }

    public static String getInvalidPassword() {
        return "qwerty123";
    }

    public static String getEmptyData() {
        return "";
    }

}

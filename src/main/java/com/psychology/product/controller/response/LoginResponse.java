package com.psychology.product.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String jwtAccessToken;
    private String jwtRefreshToken;
}

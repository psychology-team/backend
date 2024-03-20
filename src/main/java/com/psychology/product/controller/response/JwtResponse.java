package com.psychology.product.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String jwtAccessToken;
    private String jwtRefreshToken;
}

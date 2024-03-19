package com.psychology.product.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record LoginResponse(String jwtAccessToken,
                            String jwtRefreshToken) {
    public LoginResponse(String jwtAccessToken, String jwtRefreshToken) {
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }
}

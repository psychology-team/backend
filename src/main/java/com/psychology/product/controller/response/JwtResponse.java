package com.psychology.product.controller.response;

public record JwtResponse(String jwtAccessToken,
                          String jwtRefreshToken) {

}

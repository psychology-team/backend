package com.psychology.product.controller.response;

public record LoginResponse(String jwtAccessToken,
                            String jwtRefreshToken) {

}

package com.artur.hasparian.psychology.platform.controller.response;

public record JwtResponse(String jwtAccessToken,
                          String jwtRefreshToken) {

}

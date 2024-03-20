package com.psychology.product.controller;

import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.controller.response.JwtResponse;
import com.psychology.product.controller.response.LoginResponse;
import com.psychology.product.service.AuthService;
import com.psychology.product.service.UserService;
import com.psychology.product.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor

@Tag(name = "Authentication Controller", description = "Endpoints for authentication business logic")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    public ResponseEntity<?> signup(@Validated @RequestBody SignUpRequest signUpRequest) {
        userService.createNewUser(signUpRequest);
        return ResponseUtil.generateResponse("Created", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "User authentication and token issuance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid request format or data"),
            @ApiResponse(responseCode = "404", description = "User Not Found")
    })
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseUtil.generateResponse("Successfully Authenticated", HttpStatus.OK, loginResponse);
    }

    @GetMapping("/security-point")
    public void login() {
        System.out.println("Success");
    }

    @PostMapping("/refresh/access-token")
    @Operation(summary = "Refresh jwt access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> getNewAccessToken(@RequestBody JwtResponse jwtResponse) throws AuthException {
        final JwtResponse token = authService.getJwtAccessToken(jwtResponse.getJwtRefreshToken());
        return ResponseUtil.generateResponse("Access token", HttpStatus.OK, token);
    }

}

package com.psychology.product.controller;

import com.psychology.product.aspect.ExcludeAspect;
import com.psychology.product.controller.request.ActivateRequest;
import com.psychology.product.controller.request.LoginRequest;
import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.controller.response.JwtResponse;
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
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    @ExcludeAspect
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
    @ExcludeAspect
    @Operation(summary = "User authentication and token issuance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid request format or data"),
            @ApiResponse(responseCode = "404", description = "User Not Found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.loginUser(loginRequest);
        return ResponseUtil.generateResponse("Successfully Authenticated", HttpStatus.OK, jwtResponse);
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
        JwtResponse token = authService.getJwtAccessToken(jwtResponse.jwtRefreshToken());
        return ResponseUtil.generateResponse("Access token", HttpStatus.OK, token);
    }

    @PostMapping("/refresh/refresh-token")
    @Operation(summary = "Refresh jwt refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> getNewRefreshToken(@RequestBody JwtResponse jwtResponse) throws AuthException {
        JwtResponse tokens = authService.getJwtRefreshToken(jwtResponse.jwtRefreshToken());
        return ResponseUtil.generateResponse("Tokens", HttpStatus.OK, tokens);
    }

    @PostMapping("/activate/{code}")
    @ExcludeAspect
    @Operation(summary = "Activate user with activation code")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(schema = @Schema(implementation = ActivateRequest.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> activateUser(@PathVariable("code") String code) {
        userService.activateUser(code);
        return ResponseUtil.generateResponse("User activated successfully!", HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseUtil.generateResponse("Logout success", HttpStatus.OK);
    }

}

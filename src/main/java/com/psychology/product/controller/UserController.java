package com.psychology.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.constant.ApiKey;
import com.psychology.product.controller.request.ForgotPasswordRequest;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.service.UserService;
import com.psychology.product.util.JsonViews;
import com.psychology.product.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiKey.USERS)
@Slf4j
@AllArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for working with user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping(ApiKey.USERS_PROFILE)
    @JsonView(JsonViews.UserView.class)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> getUser() {
        UserDTO user = userService.getCurrentUser();
        return ResponseUtil.generateResponse("User success getting", HttpStatus.OK, user);
    }

    @PutMapping(ApiKey.USERS_PROFILE)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @JsonView(JsonViews.UserView.class)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Update current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED"),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> updateUser(@RequestBody UserDTO updated) {
        UserDTO user = userService.updateUser(updated);
        return ResponseUtil.generateResponse("User success updated", HttpStatus.OK, user);
    }

    @DeleteMapping(ApiKey.USERS_PROFILE)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Disable user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Disabled"),
            @ApiResponse(responseCode = "403", description = "Haven't permission to disable user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> disableUser() throws AuthException {
        userService.disableUser();
        return ResponseUtil.generateResponse("User was disabled.", HttpStatus.OK);
    }

    @PostMapping(ApiKey.USERS_PASSWORD_FORGOT)
    @Operation(description = "Send a unique code to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest email) {
        userService.forgotPassword(email);
        return ResponseUtil.generateResponse("Ok",HttpStatus.OK);
    }

}

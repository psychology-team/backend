package com.psychology.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.service.AdminService;
import com.psychology.product.service.UserService;
import com.psychology.product.util.JsonViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "Endpoints for working with admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/clients-all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public ResponseEntity<?> showAllUsers() {
        List<UserDTO> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<?> showOneClient(@PathVariable String id) {
        UserDTO user = adminService.getCurrentClient(UUID.fromString(id));
        return ResponseEntity.ok(user);
    }
}

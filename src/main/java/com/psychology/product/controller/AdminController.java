package com.psychology.product.controller;

import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.service.AdminService;
import com.psychology.product.service.UserService;
import com.psychology.product.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> showAllClients() {
        List<UserDTO> allUsers = userService.findAllUsers();
        return ResponseUtil.generateResponse("Users success returned", HttpStatus.OK, allUsers);
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
    public ResponseEntity<?> showClient(@PathVariable String id) {
        UserDTO user = adminService.getCurrentClient(UUID.fromString(id));
        return ResponseUtil.generateResponse("Show client details", HttpStatus.OK, user);
    }

    @PutMapping("/client/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Disable client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<?> disableClient(@PathVariable String id) {
        adminService.disableClient(UUID.fromString(id));
        return ResponseUtil.generateResponse("User was disabled.", HttpStatus.OK);
    }

    @DeleteMapping("/client/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Delete client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found"),
    })
    public ResponseEntity<?> deleteClient(@PathVariable String id) {
        adminService.deleteClient(UUID.fromString(id));
        return ResponseUtil.generateResponse("User was deleted.", HttpStatus.OK);
    }
}

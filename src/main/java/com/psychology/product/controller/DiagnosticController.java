package com.psychology.product.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.service.DiagnosticService;
import com.psychology.product.util.JsonViews;
import com.psychology.product.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/diagnostic")
@Slf4j
@AllArgsConstructor
@Tag(name = "Diagnostic Controller", description = "Endpoints works with diagnostics")
@CrossOrigin(origins = "*")
public class DiagnosticController {
    private final DiagnosticService diagnosticService;

    @GetMapping("/get-all")
    @JsonView(JsonViews.UserView.class)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all diagnostics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> getAllDiagnostics() {
        List<DiagnosticDTO> diagnostic = diagnosticService.getAllDiagnostics();
        return ResponseUtil.generateResponse("Successfully return diagnostics", HttpStatus.OK, diagnostic);
    }

    @GetMapping("/{id}")
    @JsonView(JsonViews.UserView.class)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get diagnostic by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> getDiagnosticById(@PathVariable UUID id) {
        DiagnosticDTO diagnostic = diagnosticService.getDiagnosticById(id);
        return ResponseUtil.generateResponse("Diagnostic", HttpStatus.OK, diagnostic);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add new diagnostic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<?> addDiagnostic(@Validated @RequestBody DiagnosticDTO current) {
        DiagnosticDTO diagnostic = diagnosticService.addDiagnostic(current);
        return ResponseUtil.generateResponse("Diagnostic was successfully added", HttpStatus.OK, diagnostic);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete diagnostic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Diagnostic not found")
    })
    public ResponseEntity<?> deleteDiagnostic(@PathVariable UUID id) {
        diagnosticService.deleteDiagnostic(id);
        return ResponseUtil.generateResponse("Diagnostic successfully deleted", HttpStatus.OK);
    }
}

package com.psychology.product.controller;

import com.psychology.product.constant.ApiKey;
import com.psychology.product.service.MacCardService;
import com.psychology.product.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiKey.CARD)
@Slf4j
@Tag(name = "Card Controller", description = "Endpoints works with assogit ciative cards")
@CrossOrigin(origins = "*")
public class MacCardController {
    private final MacCardService macCardService;


    @PostMapping(ApiKey.CARD_CREATE)
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Create a new card")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    public ResponseEntity<?> createCard(
            @RequestBody List<String> images
    ) {
        macCardService.createCard(images);
        return ResponseUtil.generateResponse("Success", HttpStatus.CREATED);
    }
}

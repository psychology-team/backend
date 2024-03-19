package com.psychology.product.controller;

import com.psychology.product.controller.request.SignUpRequest;
import com.psychology.product.service.UserService;
import com.psychology.product.util.ResponseHandler;
import com.psychology.product.util.ValidationHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@AllArgsConstructor

@Tag(name = "Authentication Controller")
public class AuthController {

    private final UserService userService;
    private final ValidationHandler validationHandler;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return validationHandler.bindingResultHandler(bindingResult);
        }

        userService.createNewUser(signUpRequest);
        return ResponseHandler.generateResponse("Created", HttpStatus.CREATED);

    }

}

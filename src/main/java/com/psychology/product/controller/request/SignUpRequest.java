package com.psychology.product.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(@NotNull
                            @NotBlank
                            String firstName,
                            String lastName,
                            @NotNull
                            @Email String email,
                            @NotNull
                            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
                            String password,
                            @Pattern(regexp = "^\\+38\\d{10,13}$")
                            String phone) {

}

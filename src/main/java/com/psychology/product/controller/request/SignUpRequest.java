package com.psychology.product.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(@NotNull
                            @NotBlank
                            String firstName,
                            @NotNull
                            @NotBlank
                            String lastName,
                            @NotNull
                            @Email(message = "Please provide a valid email address.") String email,
                            @NotNull
                            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                                    message = "Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.")
                            String password,
                            @Pattern(regexp = "^\\+38\\d{10,13}$",
                                    message = "Provide a valid phone number")
                            String phone) {

}

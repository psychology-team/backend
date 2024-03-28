package com.psychology.product.repository.dto;

import java.util.UUID;

public record UserDTO(UUID id,
                      String firstName,
                      String lastName,
                      String email,
                      String phone) {
}
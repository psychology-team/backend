package com.psychology.product.user;

import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Boolean enabled;
    private Instant createdTimestamp;
    private Set<UserAuthority> authorities;
}
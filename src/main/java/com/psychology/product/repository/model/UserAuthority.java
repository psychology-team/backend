package com.psychology.product.repository.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

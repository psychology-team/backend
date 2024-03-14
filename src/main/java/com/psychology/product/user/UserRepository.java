package com.psychology.product.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDAO, UUID> {
    Optional<UserDAO> findByEmail(String email);
}

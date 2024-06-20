package com.psychology.product.repository;

import com.psychology.product.repository.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<User, UUID> {
    @NotNull Optional<User> findById(@NotNull UUID id);
}

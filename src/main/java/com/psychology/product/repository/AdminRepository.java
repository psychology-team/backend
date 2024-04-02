package com.psychology.product.repository;

import com.psychology.product.repository.model.UserDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<UserDAO, UUID> {
    @NotNull Optional<UserDAO> findById(@NotNull UUID id);
}

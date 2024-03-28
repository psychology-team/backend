package com.psychology.product.repository;

import com.psychology.product.repository.model.UserDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDAO, UUID> {
    Optional<UserDAO> findByEmail(String email);
    @NotNull Optional<UserDAO> findById(@NotNull UUID uuid);
    Optional<List<UserDAO>> findAllByRevokedTimestampLessThanEqual(Instant revokedTime);

}

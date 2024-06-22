package com.psychology.product.repository;

import com.psychology.product.repository.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    @NotNull Optional<User> findById(@NotNull UUID uuid);
    Optional<List<User>> findAllByRevokedTimestampLessThanEqual(Instant revokedTime);
    Optional<User> findByUniqueCode(String uniqueCode);
}

package com.psychology.product.repository;

import com.psychology.product.repository.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    Token findByToken(String token);

    List<Token> findAllByUser_Id(UUID userId);
}

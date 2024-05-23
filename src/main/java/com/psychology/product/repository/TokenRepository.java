package com.psychology.product.repository;

import com.psychology.product.repository.model.TokenDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenDAO, UUID> {
    TokenDAO findByToken(String token);

    List<TokenDAO> findAllByUser_Email(String email);
}

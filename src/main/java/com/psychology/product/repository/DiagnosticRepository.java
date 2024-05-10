package com.psychology.product.repository;

import com.psychology.product.repository.model.DiagnosticDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticRepository extends JpaRepository<DiagnosticDAO, UUID> {
    @NotNull List<DiagnosticDAO> findAll();
}

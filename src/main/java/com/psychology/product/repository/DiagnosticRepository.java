package com.psychology.product.repository;

import com.psychology.product.repository.model.Diagnostic;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, UUID> {
    @NotNull
    Page<Diagnostic> findAll(Pageable pageable);
}

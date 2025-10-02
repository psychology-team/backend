package com.artur.hasparian.psychology.platform.repository;

import com.artur.hasparian.psychology.platform.repository.model.Diagnostic;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, UUID> {
    @NotNull
    Page<Diagnostic> findAll(Pageable pageable);
}

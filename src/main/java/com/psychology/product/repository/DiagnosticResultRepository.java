package com.psychology.product.repository;

import com.psychology.product.repository.model.DiagnosticResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResult, UUID> {
    DiagnosticResult findByUser_IdAndDiagnostic_DiagnosticId(UUID userId, UUID diagnosticID);

    List<DiagnosticResult> getAllByUserId(UUID userId);
}

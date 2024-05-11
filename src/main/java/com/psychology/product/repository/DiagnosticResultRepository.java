package com.psychology.product.repository;

import com.psychology.product.repository.model.DiagnosticResultDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosticResultRepository extends JpaRepository<DiagnosticResultDAO, UUID> {
    DiagnosticResultDAO findByUserDAO_IdAndDiagnosticDAO_DiagnosticId(UUID userId, UUID diagnosticID);

    List<DiagnosticResultDAO> getAllByUserDAO_Id(UUID userId);
}

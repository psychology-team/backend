package com.psychology.product.repository;

import com.psychology.product.repository.model.DiagnosticDAO;
import com.psychology.product.repository.model.QuestionDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionDAO, UUID> {
    List<QuestionDAO> getAllByDiagnosticDAO(DiagnosticDAO diagnosticDAO);
}

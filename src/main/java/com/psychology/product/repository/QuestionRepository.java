package com.psychology.product.repository;

import com.psychology.product.repository.model.Diagnostic;
import com.psychology.product.repository.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> getAllByDiagnostic(Diagnostic diagnostic);
}

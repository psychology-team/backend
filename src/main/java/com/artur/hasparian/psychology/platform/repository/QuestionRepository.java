package com.artur.hasparian.psychology.platform.repository;

import com.artur.hasparian.psychology.platform.repository.model.Diagnostic;
import com.artur.hasparian.psychology.platform.repository.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> getAllByDiagnostic(Diagnostic diagnostic);
}

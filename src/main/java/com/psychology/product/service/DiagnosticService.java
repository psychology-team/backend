package com.psychology.product.service;

import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.repository.dto.QuestionDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosticService {
    List<DiagnosticDTO> getAllDiagnostics();

    DiagnosticDTO getDiagnosticById(UUID id);

    DiagnosticDTO addDiagnostic(DiagnosticDTO diagnosticRequest);

    QuestionDTO addQuestion(QuestionDTO questionRequest);

    void deleteDiagnostic(UUID id);
    void deleteQuestion(UUID id);
}

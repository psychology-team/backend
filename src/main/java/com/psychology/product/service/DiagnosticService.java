package com.psychology.product.service;

import com.psychology.product.repository.dto.AnswerDTO;
import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.repository.dto.QuestionDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosticService {
    List<DiagnosticDTO> getAllDiagnostics();

    DiagnosticDTO getDiagnosticById(UUID id);

    DiagnosticDTO addDiagnostic(DiagnosticDTO diagnosticRequest);

    DiagnosticDTO modifyDiagnostic(UUID id, DiagnosticDTO diagnosticRequest);

    AnswerDTO addAnswer(AnswerDTO answerRequest);

    DiagnosticDTO modifyAnswer(UUID id, AnswerDTO answerRequest);

    QuestionDTO addQuestion(QuestionDTO questionRequest);

    DiagnosticDTO modifyQuestion(UUID id, QuestionDTO questionRequest);

    void deleteDiagnostic(UUID id);

    void deleteQuestion(UUID id);

    void deleteAnswer(UUID id);

}

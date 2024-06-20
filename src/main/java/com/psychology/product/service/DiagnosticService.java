package com.psychology.product.service;

import com.psychology.product.repository.dto.AnswerDTO;
import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.repository.dto.QuestionDTO;
import com.psychology.product.repository.dto.DiagnosticResultDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosticService {
    /**
     * Retrieves all diagnostics.
     *
     * @return a list of DiagnosticDTO objects
     */
    List<DiagnosticDTO> getAllDiagnostics();

    /**
     * Retrieves a diagnostic by its unique identifier.
     *
     * @param id the unique identifier of the diagnostic
     * @return the DiagnosticDTO object representing the diagnostic
     */
    DiagnosticDTO getDiagnosticById(UUID id);

    /**
     * Adds a new diagnostic.
     *
     * @param diagnosticRequest the DiagnosticDTO object containing diagnostic details
     * @return the added DiagnosticDTO object
     */
    DiagnosticDTO addDiagnostic(DiagnosticDTO diagnosticRequest);

    /**
     * Modifies an existing diagnostic.
     *
     * @param id the unique identifier of the diagnostic to be modified
     * @param diagnosticRequest the DiagnosticDTO object containing updated diagnostic details
     * @return the modified DiagnosticDTO object
     */
    DiagnosticDTO modifyDiagnostic(UUID id, DiagnosticDTO diagnosticRequest);

    /**
     * Adds a new answer.
     *
     * @param answerRequest the AnswerDTO object containing answer details
     * @return the added AnswerDTO object
     */
    AnswerDTO addAnswer(AnswerDTO answerRequest);

    /**
     * Modifies an existing answer.
     *
     * @param id the unique identifier of the answer to be modified
     * @param answerRequest the AnswerDTO object containing updated answer details
     * @return the modified DiagnosticDTO object containing the updated answer
     */
    DiagnosticDTO modifyAnswer(UUID id, AnswerDTO answerRequest);

    /**
     * Adds a new question.
     *
     * @param questionRequest the QuestionDTO object containing question details
     * @return the added QuestionDTO object
     */
    QuestionDTO addQuestion(QuestionDTO questionRequest);

    /**
     * Modifies an existing question.
     *
     * @param id the unique identifier of the question to be modified
     * @param questionRequest the QuestionDTO object containing updated question details
     * @return the modified DiagnosticDTO object containing the updated question
     */
    DiagnosticDTO modifyQuestion(UUID id, QuestionDTO questionRequest);

    /**
     * Deletes a diagnostic by its unique identifier.
     *
     * @param id the unique identifier of the diagnostic to be deleted
     */
    void deleteDiagnostic(UUID id);

    /**
     * Deletes a question by its unique identifier.
     *
     * @param id the unique identifier of the question to be deleted
     */
    void deleteQuestion(UUID id);

    /**
     * Deletes an answer by its unique identifier.
     *
     * @param id the unique identifier of the answer to be deleted
     */
    void deleteAnswer(UUID id);

    /**
     * Records the result of a passed diagnostic for the user.
     *
     * @param userDiagnosticResultRequest the DiagnosticResultDTO object containing the user's diagnostic result details
     * @return the recorded DiagnosticResultDTO object
     */
    DiagnosticResultDTO passedDiagnosticResult(DiagnosticResultDTO userDiagnosticResultRequest);

    /**
     * Retrieves the diagnostic results for the current user.
     *
     * @return a list of DiagnosticResultDTO objects representing the user's diagnostic results
     */
    List<DiagnosticResultDTO> getDiagnosticResultForCurrentUser();
}

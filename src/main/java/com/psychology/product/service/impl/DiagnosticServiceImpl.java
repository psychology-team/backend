package com.psychology.product.service.impl;

import com.psychology.product.repository.AnswerRepository;
import com.psychology.product.repository.DiagnosticRepository;
import com.psychology.product.repository.DiagnosticResultRepository;
import com.psychology.product.repository.QuestionRepository;
import com.psychology.product.repository.dto.*;
import com.psychology.product.repository.model.*;
import com.psychology.product.service.DiagnosticService;
import com.psychology.product.service.mapper.*;
import com.psychology.product.util.exception.ConflictException;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosticServiceImpl implements DiagnosticService {
    private final DiagnosticRepository diagnosticRepository;
    private final DiagnosticResultRepository diagnosticResultRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DiagnosticMapper diagnosticMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final DiagnosticResultMapper diagnosticResultMapper;

    @Override
    public List<DiagnosticDTO> getAllDiagnostics() {
        List<Diagnostic> diagnosticList = diagnosticRepository.findAll();
        return diagnosticMapper.toDTO(diagnosticList);
    }

    public DiagnosticDTO getDiagnosticById(UUID id) {
        Diagnostic diagnostic = diagnosticRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        return diagnosticMapper.toDTO(diagnostic);
    }

    @Override
    public DiagnosticDTO addDiagnostic(DiagnosticDTO diagnosticRequest) {
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setDiagnosticName(diagnosticRequest.diagnosticName());
        diagnostic.setDiagnosticDescription(diagnosticRequest.diagnosticDescription());

        diagnosticRepository.save(diagnostic);
        return diagnosticMapper.toDTO(diagnostic);
    }

    @Override
    public DiagnosticDTO modifyDiagnostic(UUID id, DiagnosticDTO diagnosticRequest) {
        Diagnostic diagnostic = diagnosticRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        diagnostic.setDiagnosticName(diagnosticRequest.diagnosticName());
        diagnostic.setDiagnosticDescription(diagnosticRequest.diagnosticDescription());

        diagnosticRepository.save(diagnostic);
        return diagnosticMapper.toDTO(diagnostic);
    }

    @Override
    public AnswerDTO addAnswer(AnswerDTO answerRequest) {
        Question question = questionRepository.findById(answerRequest.questionId())
                .orElseThrow(() -> new NotFoundException("Question not found"));
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setAnswerText(answerRequest.answerText());
        answer.setScalePoints(answerRequest.scalePoints());
        answer.setInterpretationPoints(answerRequest.interpretationPoints());

        answerRepository.save(answer);
        return answerMapper.toDTO(answer);
    }

    @Override
    public DiagnosticDTO modifyAnswer(UUID id, AnswerDTO answerRequest) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found"));
        answer.setAnswerText(answerRequest.answerText());
        answer.setInterpretationPoints(answerRequest.interpretationPoints());
        answer.setScalePoints(answerRequest.scalePoints());

        answerRepository.save(answer);
        return diagnosticMapper.toDTO(answer.getQuestion().getDiagnostic());
    }

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionRequest) {
        Diagnostic diagnostic = diagnosticRepository.findById(questionRequest.diagnosticId())
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));

        Question question = new Question();
        question.setDiagnostic(diagnostic);
        question.setQuestionText(questionRequest.questionText());
        questionRepository.save(question);

        return questionMapper.toDTO(question);
    }

    @Override
    public DiagnosticDTO modifyQuestion(UUID id, QuestionDTO questionRequest) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        question.setQuestionText(questionRequest.questionText());

        questionRepository.save(question);
        return diagnosticMapper.toDTO(question.getDiagnostic());
    }

    @Override
    public void deleteDiagnostic(UUID id) {
        Diagnostic diagnostic = diagnosticRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        diagnosticRepository.delete(diagnostic);
    }

    @Override
    public void deleteQuestion(UUID id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        questionRepository.delete(question);
    }

    @Override
    public void deleteAnswer(UUID id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found"));
        answerRepository.delete(answer);
    }

    @Override
    public DiagnosticResultDTO passedDiagnosticResult(DiagnosticResultDTO diagnosticResultRequest) {
        Optional.ofNullable(diagnosticResultRepository.findByUser_IdAndDiagnostic_DiagnosticId(diagnosticResultRequest.userId(), diagnosticResultRequest.diagnosticId()))
                .ifPresent(diagnosticResultDAO -> {
                    throw new ConflictException("Result for this diagnostic already exist");
                });

        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticResultRequest.diagnosticId())
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        UserDTO userDTO = userService.getCurrentUser();

        if (!userDTO.id().equals(diagnosticResultRequest.userId()))
            throw new ConflictException("You don't have permission for add result for another user");

        User user = userMapper.toDAO(userDTO);

        DiagnosticResult diagnosticResult = new DiagnosticResult();
        diagnosticResult.setDiagnostic(diagnostic);
        diagnosticResult.setUser(user);
        diagnosticResult.setScalePoints(diagnosticResultRequest.scalePoints());
        diagnosticResult.setInterpretationPoints(diagnosticResultRequest.interpretationPoints());

        diagnosticResultRepository.save(diagnosticResult);
        return diagnosticResultMapper.toDTO(diagnosticResult);
    }

    @Override
    public List<DiagnosticResultDTO> getDiagnosticResultForCurrentUser() {
        UserDTO userDTO = userService.getCurrentUser();
        List<DiagnosticResult> diagnosticResultList = diagnosticResultRepository.getAllByUserId(userDTO.id());
        return diagnosticResultMapper.toDTO(diagnosticResultList);
    }
}

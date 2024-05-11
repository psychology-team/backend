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
        List<DiagnosticDAO> diagnosticDAOList = diagnosticRepository.findAll();
        return diagnosticMapper.toDTO(diagnosticDAOList);
    }

    public DiagnosticDTO getDiagnosticById(UUID id) {
        DiagnosticDAO diagnosticDAO = diagnosticRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        return diagnosticMapper.toDTO(diagnosticDAO);
    }

    @Override
    public DiagnosticDTO addDiagnostic(DiagnosticDTO diagnosticRequest) {
        DiagnosticDAO diagnostic = new DiagnosticDAO();
        diagnostic.setDiagnosticName(diagnosticRequest.diagnosticName());
        diagnostic.setDiagnosticDescription(diagnosticRequest.diagnosticDescription());

        diagnosticRepository.save(diagnostic);
        return diagnosticMapper.toDTO(diagnostic);
    }

    @Override
    public DiagnosticDTO modifyDiagnostic(UUID id, DiagnosticDTO diagnosticRequest) {
        DiagnosticDAO diagnostic = diagnosticRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        diagnostic.setDiagnosticName(diagnosticRequest.diagnosticName());
        diagnostic.setDiagnosticDescription(diagnosticRequest.diagnosticDescription());

        diagnosticRepository.save(diagnostic);
        return diagnosticMapper.toDTO(diagnostic);
    }

    @Override
    public AnswerDTO addAnswer(AnswerDTO answerRequest) {
        QuestionDAO question = questionRepository.findById(answerRequest.questionId())
                .orElseThrow(() -> new NotFoundException("Question not found"));
        AnswerDAO answer = new AnswerDAO();
        answer.setQuestionDAO(question);
        answer.setAnswerText(answerRequest.answerText());
        answer.setScalePoints(answerRequest.scalePoints());
        answer.setInterpretationPoints(answerRequest.interpretationPoints());

        answerRepository.save(answer);
        return answerMapper.toDTO(answer);
    }

    @Override
    public DiagnosticDTO modifyAnswer(UUID id, AnswerDTO answerRequest) {
        AnswerDAO answer = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found"));
        answer.setAnswerText(answerRequest.answerText());
        answer.setInterpretationPoints(answerRequest.interpretationPoints());
        answer.setScalePoints(answerRequest.scalePoints());

        answerRepository.save(answer);
        return diagnosticMapper.toDTO(answer.getQuestionDAO().getDiagnosticDAO());
    }

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionRequest) {
        DiagnosticDAO diagnostic = diagnosticRepository.findById(questionRequest.diagnosticId())
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));

        QuestionDAO question = new QuestionDAO();
        question.setDiagnosticDAO(diagnostic);
        question.setQuestionText(questionRequest.questionText());
        questionRepository.save(question);

        return questionMapper.toDTO(question);
    }

    @Override
    public DiagnosticDTO modifyQuestion(UUID id, QuestionDTO questionRequest) {
        QuestionDAO question = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        question.setQuestionText(questionRequest.questionText());

        questionRepository.save(question);
        return diagnosticMapper.toDTO(question.getDiagnosticDAO());
    }

    @Override
    public void deleteDiagnostic(UUID id) {
        DiagnosticDAO diagnosticDAO = diagnosticRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        diagnosticRepository.delete(diagnosticDAO);
    }

    @Override
    public void deleteQuestion(UUID id) {
        QuestionDAO questionDAO = questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Question not found"));
        questionRepository.delete(questionDAO);
    }

    @Override
    public void deleteAnswer(UUID id) {
        AnswerDAO answerDAO = answerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Answer not found"));
        answerRepository.delete(answerDAO);
    }

    @Override
    public DiagnosticResultDTO passedDiagnosticResult(DiagnosticResultDTO diagnosticResultRequest) {
        Optional.ofNullable(diagnosticResultRepository.findByUserDAO_IdAndDiagnosticDAO_DiagnosticId(diagnosticResultRequest.userId(), diagnosticResultRequest.diagnosticId()))
                .ifPresent(diagnosticResultDAO -> {
                    throw new ConflictException("Result for this diagnostic already exist");
                });

        DiagnosticDAO diagnosticDAO = diagnosticRepository.findById(diagnosticResultRequest.diagnosticId())
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));
        UserDTO userDTO = userService.getCurrentUser();

        if (!userDTO.id().equals(diagnosticResultRequest.userId()))
            throw new ConflictException("You don't have permission for add result for another user");

        UserDAO user = userMapper.toDAO(userDTO);

        DiagnosticResultDAO diagnosticResultDAO = new DiagnosticResultDAO();
        diagnosticResultDAO.setDiagnosticDAO(diagnosticDAO);
        diagnosticResultDAO.setUserDAO(user);
        diagnosticResultDAO.setScalePoints(diagnosticResultRequest.scalePoints());
        diagnosticResultDAO.setInterpretationPoints(diagnosticResultRequest.interpretationPoints());

        diagnosticResultRepository.save(diagnosticResultDAO);
        return diagnosticResultMapper.toDTO(diagnosticResultDAO);
    }

    @Override
    public List<DiagnosticResultDTO> getDiagnosticResultForCurrentUser() {
        UserDTO userDTO = userService.getCurrentUser();
        List<DiagnosticResultDAO> diagnosticResultDAOList = diagnosticResultRepository.getAllByUserDAO_Id(userDTO.id());
        return diagnosticResultMapper.toDTO(diagnosticResultDAOList);
    }
}

package com.psychology.product.service.impl;

import com.psychology.product.repository.AnswerRepository;
import com.psychology.product.repository.DiagnosticRepository;
import com.psychology.product.repository.QuestionRepository;
import com.psychology.product.repository.dto.AnswerDTO;
import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.repository.dto.QuestionDTO;
import com.psychology.product.repository.model.AnswerDAO;
import com.psychology.product.repository.model.DiagnosticDAO;
import com.psychology.product.repository.model.QuestionDAO;
import com.psychology.product.service.DiagnosticService;
import com.psychology.product.service.mapper.AnswerMapper;
import com.psychology.product.service.mapper.DiagnosticMapper;
import com.psychology.product.service.mapper.QuestionMapper;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosticServiceImpl implements DiagnosticService {
    private final DiagnosticRepository diagnosticRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DiagnosticMapper diagnosticMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

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
}

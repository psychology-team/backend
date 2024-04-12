package com.psychology.product.service.impl;

import com.psychology.product.repository.AnswerRepository;
import com.psychology.product.repository.DiagnosticRepository;
import com.psychology.product.repository.QuestionRepository;
import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.repository.model.DiagnosticDAO;
import com.psychology.product.service.DiagnosticService;
import com.psychology.product.service.mapper.AnswerMapper;
import com.psychology.product.service.mapper.DiagnosticMapper;
import com.psychology.product.service.mapper.QuestionMapper;
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
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DiagnosticMapper diagnosticMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    @Override
    public List<DiagnosticDTO> getAllDiagnosticDetailsById(UUID diagnosticId) {
        DiagnosticDAO diagnosticDAO = Optional.of(diagnosticRepository.getByDiagnosticId(diagnosticId))
                .orElseThrow(() -> new NotFoundException("Diagnostic not found"));

        System.out.println(diagnosticDAO);

        List<DiagnosticDAO> diagnosticDAOList = diagnosticRepository.getAllByDiagnosticId(diagnosticId);
//        DiagnosticDTO diagnosticDTO = diagnosticMapper.toDTO(diagnosticDAO);
        return diagnosticMapper.toDTO(diagnosticDAOList);
    }
}

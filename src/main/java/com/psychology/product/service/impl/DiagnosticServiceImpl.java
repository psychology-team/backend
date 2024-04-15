package com.psychology.product.service.impl;

import com.psychology.product.repository.DiagnosticRepository;
import com.psychology.product.repository.dto.DiagnosticDTO;
import com.psychology.product.repository.model.DiagnosticDAO;
import com.psychology.product.service.DiagnosticService;
import com.psychology.product.service.mapper.DiagnosticMapper;
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
    private final DiagnosticMapper diagnosticMapper;

    @Override
    public List<DiagnosticDTO> getAllDiagnostics() {
        List<DiagnosticDAO> diagnosticDAOList = diagnosticRepository.findAll();
        return diagnosticMapper.toDTO(diagnosticDAOList);
    }

    public DiagnosticDTO getDiagnosticById(UUID id) {
        DiagnosticDAO diagnosticDAO = diagnosticRepository.findById(id).orElse(null);
        return diagnosticMapper.toDTO(diagnosticDAO);
    }

    @Override
    public DiagnosticDTO addDiagnostic(DiagnosticDTO current) {
        UUID diagnosticId = UUID.randomUUID();
        DiagnosticDAO diagnostic = new DiagnosticDAO();
        diagnostic.setDiagnosticId(diagnosticId);
        Optional.ofNullable(current.diagnosticId()).ifPresent(diagnostic::setDiagnosticId);
        Optional.ofNullable(current.diagnosticName()).ifPresent(diagnostic::setDiagnosticName);

        diagnosticRepository.save(diagnostic);
        return diagnosticMapper.toDTO(diagnostic);
    }

}

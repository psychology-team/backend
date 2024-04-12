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
}

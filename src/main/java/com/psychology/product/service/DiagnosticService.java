package com.psychology.product.service;

import com.psychology.product.repository.dto.DiagnosticDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosticService {
    List<DiagnosticDTO> getAllDiagnosticDetailsById(UUID diagnosticId);
}

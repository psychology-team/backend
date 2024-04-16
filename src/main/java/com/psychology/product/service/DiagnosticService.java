package com.psychology.product.service;

import com.psychology.product.repository.dto.DiagnosticDTO;

import java.util.List;
import java.util.UUID;

public interface DiagnosticService {
    List<DiagnosticDTO> getAllDiagnostics();

    DiagnosticDTO getDiagnosticById(UUID id);

    DiagnosticDTO addDiagnostic(DiagnosticDTO current);

    void deleteDiagnostic(UUID id);
}

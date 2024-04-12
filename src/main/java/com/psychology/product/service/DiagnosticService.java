package com.psychology.product.service;

import com.psychology.product.repository.dto.DiagnosticDTO;

import java.util.List;

public interface DiagnosticService {
    List<DiagnosticDTO> getAllDiagnostics();
}

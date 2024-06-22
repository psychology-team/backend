package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.DiagnosticResultDTO;
import com.psychology.product.repository.model.DiagnosticResult;

import java.util.List;

public interface DiagnosticResultMapper {
    DiagnosticResultDTO toDTO(DiagnosticResult result);

    List<DiagnosticResultDTO> toDTO(List<DiagnosticResult> results);

}

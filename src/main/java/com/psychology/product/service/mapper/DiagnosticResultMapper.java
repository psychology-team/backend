package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.DiagnosticResultDTO;
import com.psychology.product.repository.model.DiagnosticResultDAO;

import java.util.List;

public interface DiagnosticResultMapper {
    DiagnosticResultDTO toDTO(DiagnosticResultDAO result);

    List<DiagnosticResultDTO> toDTO(List<DiagnosticResultDAO> results);

}

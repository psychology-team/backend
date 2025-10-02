package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.DiagnosticResultDTO;
import com.artur.hasparian.psychology.platform.repository.model.DiagnosticResult;

import java.util.List;

public interface DiagnosticResultMapper {
    DiagnosticResultDTO toDTO(DiagnosticResult result);

    List<DiagnosticResultDTO> toDTO(List<DiagnosticResult> results);

}

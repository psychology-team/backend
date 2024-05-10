package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.DiagnosticResultDTO;
import com.psychology.product.repository.model.DiagnosticResultDAO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DiagnosticResultMapper {
    DiagnosticResultDTO toDTO(DiagnosticResultDAO result);
}

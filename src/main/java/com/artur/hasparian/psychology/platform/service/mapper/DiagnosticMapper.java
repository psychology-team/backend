package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.DiagnosticDTO;
import com.artur.hasparian.psychology.platform.repository.model.Diagnostic;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {QuestionMapper.class, AnswerMapper.class})
public interface DiagnosticMapper {
    DiagnosticDTO toDTO(Diagnostic diagnostic);

    List<DiagnosticDTO> toDTO(List<Diagnostic> diagnostics);

    default Page<DiagnosticDTO> toDTO(Page<Diagnostic> diagnostics) {
        return diagnostics.map(this::toDTO);
    }
}

package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.AnswerDTO;
import com.artur.hasparian.psychology.platform.repository.model.Answer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AnswerMapper {
    AnswerDTO toDTO(Answer answer);

    List<AnswerDTO> toDTO(List<Answer> answers);
}

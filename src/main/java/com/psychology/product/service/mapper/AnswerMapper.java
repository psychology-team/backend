package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.AnswerDTO;
import com.psychology.product.repository.model.Answer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AnswerMapper {
    AnswerDTO toDTO(Answer answer);

    List<AnswerDTO> toDTO(List<Answer> answers);
}

package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.QuestionDTO;
import com.psychology.product.repository.model.Question;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {AnswerMapper.class})
public interface QuestionMapper {
    QuestionDTO toDTO(Question question);

    List<QuestionDTO> toDTO(List<Question> questions);
}

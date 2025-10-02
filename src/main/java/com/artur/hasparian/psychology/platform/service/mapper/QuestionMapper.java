package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.QuestionDTO;
import com.artur.hasparian.psychology.platform.repository.model.Question;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {AnswerMapper.class})
public interface QuestionMapper {
    QuestionDTO toDTO(Question question);

    List<QuestionDTO> toDTO(List<Question> questions);
}

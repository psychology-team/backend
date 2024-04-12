package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.AnswerDTO;
import com.psychology.product.repository.model.AnswerDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerDTO toDTO(AnswerDAO answer);
//    List<AnswerDTO> toDTO(List<AnswerDAO> answerDAO);
}

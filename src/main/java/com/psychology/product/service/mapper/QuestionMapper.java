package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.QuestionDTO;
import com.psychology.product.repository.model.QuestionDAO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    List<QuestionDTO> toDTO(List<QuestionDAO> daoList);

    QuestionDTO toDTO(QuestionDAO question);

//    public default QuestionDTO toDTO(QuestionDAO question) {
//        if (question == null) {
//            return null;
//        }
//
//        UUID questionId = question.getQuestionId();
//        String questionText = question.getQuestionText();
//        UUID diagnosticId = question.getDiagnosticDAO().getDiagnosticId();
//        List<AnswerDTO> answerDTOList = null;
//
//        return new QuestionDTO(questionId, diagnosticId, questionText, null);
//    }
}

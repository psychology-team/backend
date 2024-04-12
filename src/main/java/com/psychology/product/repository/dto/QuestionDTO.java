package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;

import java.util.List;
import java.util.UUID;

@JsonView(JsonViews.Question.class)
public record QuestionDTO(@JsonProperty("question_id")
                          UUID questionId,
                          @JsonProperty("diagnostic_id")
                          @JsonInclude(JsonInclude.Include.NON_NULL)
                          UUID diagnosticId,
                          @JsonProperty("question_text")
                          String questionText,
                          @JsonProperty("answers")
                          List<AnswerDTO> answerDTOList) {
    public QuestionDTO addAnswersList(List<AnswerDTO> answersList) {
        return new QuestionDTO(this.questionId, this.diagnosticId, this.questionText, answersList);
    }
}

package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JsonView(JsonViews.Question.class)
public record QuestionDTO(@JsonProperty("question_id")
                          UUID questionId,
                          @JsonProperty("diagnostic_id")
                          @JsonInclude(JsonInclude.Include.NON_NULL)
                          UUID diagnosticId,
                          @JsonProperty("question_text")
                          @NotNull
                          @NotBlank
                          String questionText,
                          @JsonProperty("answers")
                          @JsonInclude(JsonInclude.Include.NON_NULL)
                          List<AnswerDTO> answersList) {
}

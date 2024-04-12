package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;

import java.util.UUID;

@JsonView(JsonViews.Answer.class)
public record AnswerDTO(@JsonProperty("answer_id")
                        UUID answerId,
                        @JsonProperty("question_id")
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        UUID questionId,
                        @JsonProperty("answer_text")
                        String answerText,
                        @JsonProperty("interpretation_points")
                        short interpretationPoints,
                        @JsonProperty("scale_points")
                        short scalePoints
) {
}

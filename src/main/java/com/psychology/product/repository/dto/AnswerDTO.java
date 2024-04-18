package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonView(JsonViews.Answer.class)
public record AnswerDTO(@JsonProperty("answer_id")
                        UUID answerId,
                        @JsonProperty("question_id")
                        @JsonInclude(JsonInclude.Include.NON_NULL)
                        UUID questionId,
                        @JsonProperty("answer_text")
                        @NotNull
                        @NotBlank
                        String answerText,
                        @JsonProperty("interpretation_points")
                        @NotNull
                        @Min(0)
                        @Max(2)
                        short interpretationPoints,
                        @JsonProperty("scale_points")
                        @NotNull
                        @Min(0)
                        @Max(2)
                        short scalePoints
) {
}

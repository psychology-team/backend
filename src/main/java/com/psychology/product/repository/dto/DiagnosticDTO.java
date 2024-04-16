package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JsonView(JsonViews.Diagnostic.class)
public record DiagnosticDTO(
        @JsonProperty("diagnostic_id")
        UUID diagnosticId,
        @JsonProperty("diagnostic_name")
        @NotNull
        @NotBlank
        String diagnosticName,
        @JsonProperty("diagnostic_description")
        @NotNull
        @NotBlank
        String diagnosticDescription,
        @JsonProperty("diagnostic_questions")
        List<QuestionDTO> questionsList
) {
}
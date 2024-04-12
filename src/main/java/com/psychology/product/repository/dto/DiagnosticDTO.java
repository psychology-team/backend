package com.psychology.product.repository.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;

import java.util.List;
import java.util.UUID;

@JsonView(JsonViews.Diagnostic.class)
public record DiagnosticDTO(
        @JsonProperty("diagnostic_id")
        UUID diagnosticId,
        @JsonProperty("diagnostic_name")
        String diagnosticName,
        @JsonProperty("diagnostic_description")
        String diagnosticDescription,
        @JsonProperty("diagnostic_questions")
        List<QuestionDTO> questionsList
) {
    public DiagnosticDTO addQuestionsList(List<QuestionDTO> questionsList) {
        return new DiagnosticDTO(this.diagnosticId, this.diagnosticName, this.diagnosticDescription, questionsList);
    }
}
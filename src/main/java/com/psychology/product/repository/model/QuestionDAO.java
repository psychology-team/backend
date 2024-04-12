package com.psychology.product.repository.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.psychology.product.util.JsonViews;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")
@JsonView(JsonViews.Question.class)
public class QuestionDAO implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "question_id", updatable = false, nullable = false)
    private UUID questionId;

    @ManyToOne
    @JoinColumn(name = "diagnostic_id")
    private DiagnosticDAO diagnosticDAO;

    @Column(name = "question_text")
    private String questionText;

    @Transient
    @OneToMany(mappedBy = "question_id", cascade = CascadeType.ALL)
    private List<AnswerDAO> answerDAOList;

}

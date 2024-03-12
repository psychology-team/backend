package com.psychology.product.block.test.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "questions")

public class Question {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "question_id", updatable = false, nullable = false)
    private UUID questionId;

    @NotNull
    @Column(name = "question_text")
    private String questionText;

    @Transient
    private List<Answer> answerList;

}

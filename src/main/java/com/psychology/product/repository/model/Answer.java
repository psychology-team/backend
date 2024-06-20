package com.psychology.product.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answers")

public class Answer implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "answer_id", updatable = false, nullable = false)
    private UUID answerId;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "scale_points")
    private short scalePoints;

    @Column(name = "interpretation_points")
    private short interpretationPoints;

}

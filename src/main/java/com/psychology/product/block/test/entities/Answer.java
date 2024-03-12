package com.psychology.product.block.test.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answers")

public class Answer {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "answer_id", updatable = false, nullable = false)
    private UUID answerId;

    @NotNull
    @Column(name = "answer_text")
    private String answerText;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @NotNull
    @OneToOne
    @JoinColumn(name = "scale_id")
    private Scale scale;

    @NotNull
    @OneToOne
    @JoinColumn(name = "interpretation_id")
    private Interpretation interpretation;

}

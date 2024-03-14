package com.psychology.product.diagnostic.answer;

import com.psychology.product.diagnostic.question.QuestionDAO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answers")

public class AnswerDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "answer_id", updatable = false, nullable = false)
    private UUID answerId;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionDAO questionDAO;

    @Column(name = "scale_points")
    private short scalePoints;

    @Column(name = "interpretation_points")
    private short interpretationPoints;

}

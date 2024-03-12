package com.psychology.product.test.entity.answer;

import com.psychology.product.test.entity.interpretation.InterpretationDAO;
import com.psychology.product.test.entity.question.QuestionDAO;
import com.psychology.product.test.entity.scale.ScaleDAO;
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

public class AnswerDAO {

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
    private QuestionDAO questionDAO;

    @NotNull
    @OneToOne
    @JoinColumn(name = "scale_id")
    private ScaleDAO scaleDAO;

    @NotNull
    @OneToOne
    @JoinColumn(name = "interpretation_id")
    private InterpretationDAO interpretationDAO;

}

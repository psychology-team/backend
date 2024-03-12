package com.psychology.product.test.entity.useranswer;

import com.psychology.product.test.entity.answer.AnswerDAO;
import com.psychology.product.test.entity.question.QuestionDAO;
import com.psychology.product.user.UserDAO;
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
@Table(name = "user_answers")

public class UserAnswerDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "user_answer_id", updatable = false, nullable = false)
    private UUID answerId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id")
    private UserDAO user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionDAO questionDAO;

    @NotNull
    @OneToOne
    @JoinColumn(name = "answer_id")
    private AnswerDAO answerDAO;

}

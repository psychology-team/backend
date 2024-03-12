package com.psychology.product.block.test.entities;

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

public class UserAnswer {

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
    private Question question;

    @NotNull
    @OneToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

}

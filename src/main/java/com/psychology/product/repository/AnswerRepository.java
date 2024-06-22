package com.psychology.product.repository;

import com.psychology.product.repository.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> getAllByQuestion_QuestionId(UUID questionId);
    Answer getByQuestion_QuestionId(UUID questionId);
}

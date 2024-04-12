package com.psychology.product.repository;

import com.psychology.product.repository.model.AnswerDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<AnswerDAO, UUID> {
    List<AnswerDAO> getAllByQuestionDAO_QuestionId(UUID questionId);
    AnswerDAO getByQuestionDAO_QuestionId(UUID questionId);
}

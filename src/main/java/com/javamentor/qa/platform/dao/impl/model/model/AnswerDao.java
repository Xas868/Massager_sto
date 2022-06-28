package com.javamentor.qa.platform.dao.impl.model.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;

import java.util.Optional;

public interface AnswerDao extends ReadWriteDao<Answer, Long> {

    Optional<Answer> getAnswerWithAuthor(long answerId);
    void deleteById(Long id);
}

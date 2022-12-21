package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerService extends ReadWriteService<Answer, Long> {

    Optional<Answer> getAnswerWithAuthor(Long answerId);
    void deleteById(Long id);
    Optional<Long> getCountOfAnswerVoteByQuestionId(Long id);
    Optional<Long> getCountOfAnswerToQuestionByQuestionId(Long id);
}

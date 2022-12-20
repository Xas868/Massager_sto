package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface QuestionService extends ReadWriteService<Question, Long> {
    Optional<Long> getCountByQuestion();
    Optional<List<Long>> getAllQuestionIdByTagId(Long id);

    Optional<Question> getQuestionByIdWithAuthor(Long id);

    Long getCountOfViewByQuestionId(Long id);
    Long getCountOfVoteByQuestionId(Long id);
}

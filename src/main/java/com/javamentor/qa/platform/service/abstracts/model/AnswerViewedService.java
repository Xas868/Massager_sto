package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerViewed;
import com.javamentor.qa.platform.models.entity.user.User;

public interface AnswerViewedService extends ReadWriteService<AnswerViewed, Long>  {
    void markAnswerLikeViewed(User user, Answer answer);
}
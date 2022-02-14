package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface QuestionViewedService extends ReadWriteService<QuestionViewed, Long>  {

    void markQuestionLikeViewed(User user, Question question);

}

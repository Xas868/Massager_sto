package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.question.answer.AnswerViewed;
import java.util.List;

public interface AnswerViewedDao extends ReadWriteDao<AnswerViewed, Long>{
    List<AnswerViewed> getAnswerViewedByUserAndQuestion (String email, Long questionId);
}
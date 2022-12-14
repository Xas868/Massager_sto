package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerViewedDao;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerViewedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnswerViewedServiceImpl extends ReadWriteServiceImpl<AnswerViewed, Long> implements AnswerViewedService {

    private final AnswerService answerService;
    private final AnswerViewedDao answerViewedDao;

    public AnswerViewedServiceImpl(AnswerService answerService, AnswerViewedDao answerViewedDao) {
        super(answerViewedDao);
        this.answerService = answerService;
        this.answerViewedDao = answerViewedDao;
    }

    @Override
    public void markAnswerLikeViewed(User user, Answer answer) {

        if(answerViewedDao.getAnswerViewedByUserAndQuestion(user.getEmail(), answer.getId()).isEmpty()){
            persist(AnswerViewed.builder()
                    .answer(answer)
                    .user(user)
                    .build());
        }
    }
}
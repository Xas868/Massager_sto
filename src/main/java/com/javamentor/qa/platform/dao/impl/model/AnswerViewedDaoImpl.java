package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerViewedDao;
import com.javamentor.qa.platform.models.entity.question.answer.AnswerViewed;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerViewedDaoImpl extends ReadWriteDaoImpl<AnswerViewed, Long> implements AnswerViewedDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "answerViewed", key = "#answerId+#email")
    public List<AnswerViewed> getAnswerViewedByUserAndQuestion(String email, Long answerId) {
        return entityManager.createQuery(
                        "select a from AnswerViewed a where a.user.email =:email and a.user.id =:answerId", AnswerViewed.class)
                .setParameter("email", email)
                .setParameter("answerId", answerId)
                .getResultList();
    }

    @Override
    @CacheEvict(value = "AnswerViewed", key = "#answerViewed.answer.id+#answerViewed.user.email")
    public void persist(AnswerViewed answerViewed) {
        super.persist(answerViewed);
    }
}
package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionViewedDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.QuestionViewed;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionViewedDaoImpl extends ReadWriteDaoImpl<QuestionViewed, Long> implements QuestionViewedDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Cacheable(value = "QuestionViewed", key = "#questionId+#email")
    public List<QuestionViewed> getQuestionViewedByUserAndQuestion(String email, Long questionId) {
        return entityManager.createQuery(
                        "select q from QuestionViewed q where q.user.email =:email and q.question.id =:questionId", QuestionViewed.class)
                .setParameter("email", email)
                .setParameter("questionId", questionId)
                .getResultList();
    }

    @Caching(
            evict = {@CacheEvict(
                    value = "QuestionViewed",
                    key = "#e.question.id+#e.user.email",
                    condition="#e instanceof T(com.javamentor.qa.platform.models.entity.question.QuestionViewed)")}
    )
    @Override
    public void persist(QuestionViewed e) {
        entityManager.persist(e);
    }
}

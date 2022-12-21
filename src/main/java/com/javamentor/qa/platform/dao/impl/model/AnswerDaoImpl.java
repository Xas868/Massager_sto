package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class AnswerDaoImpl extends ReadWriteDaoImpl<Answer, Long> implements AnswerDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<Answer> getAnswerWithAuthor(long answerId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "SELECT a FROM Answer a JOIN User u ON a.user.id = u.id WHERE a.id = :answerId", Answer.class)
                .setParameter("answerId", answerId));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        String hql = "UPDATE Answer a SET a.isDeleted = true WHERE a.id = :id";
        entityManager.createQuery(hql).setParameter("id", id).executeUpdate();
    }

    @Override
    public Optional<Long> getCountOfAnswerVoteByQuestionId(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("select coalesce(sum(case when va.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer va where va.answer.question.id = :id", Long.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<Long> getCountOfAnswerToQuestionByQuestionId(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("select count (a) from Answer a where a.question.id = :id", Long.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Transactional
    @Override
    public void update(Answer answer) {
        String htmlBody = answer.getHtmlBody();
        Long id = answer.getId();
        String hql = "UPDATE Answer a SET a.htmlBody = :htmlBody WHERE a.id = :id";
        entityManager.createQuery(hql)
                .setParameter("id", id)
                .setParameter("htmlBody", htmlBody)
                .executeUpdate();
    }
}
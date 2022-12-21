package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.question.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class QuestionDaoImpl extends ReadWriteDaoImpl<Question, Long> implements QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Long> getCountQuestion() {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("" +
                "SELECT COUNT(q.id) FROM Question q where q.isDeleted=false", Long.class));
    }

    @Override
    public Optional<Question> getQuestionByIdWithAuthor(Long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "select q from Question q inner join User u on q.user.id = u.id where q.id=:id", Question.class)
                .setParameter("id", id));
    }

    @Override
    public Optional<List<Long>> getAllQuestionIdByTagId(Long id) {
        return Optional.ofNullable(
                (List<Long>) entityManager.createNativeQuery("select q.question_id from question_has_tag q where q.tag_id = :id")
                        .setParameter("id", id)
                        .getResultList().stream()
                        .map(bInt -> Long.parseLong(bInt.toString()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Long> getCountOfViewByQuestionId(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("select count (qv.id) from QuestionViewed qv where qv.question.id = :id", Long.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<Long> getCountOfVoteByQuestionId(Long id) {
        return Optional.ofNullable(
                entityManager.createQuery("select sum(case when v.vote = 'UP_VOTE' then 1 else -1 end) from VoteQuestion v where v.question.id = :id", Long.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }
}
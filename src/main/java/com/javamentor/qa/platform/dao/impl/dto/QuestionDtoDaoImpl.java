package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.QuestionDtoDao;
import com.javamentor.qa.platform.dao.impl.dto.transformer.QuestionDtoResultTransformer;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.dto.question.QuestionCommentDto;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionDtoDaoImpl implements QuestionDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionCommentDto> getQuestionIdComment(Long id) {
        return entityManager.createQuery(
                        "SELECT NEW com.javamentor.qa.platform.models.dto.question.QuestionCommentDto( " +
                                "c.comment.id," +
                                "c.question.id," +
                                "c.comment.lastUpdateDateTime," +
                                "c.comment.persistDateTime," +
                                "c.comment.text," +
                                "c.comment.user.id," +
                                "c.comment.user.imageLink," +
                                "(select sum(r.count) + 0L from Reputation r where r.author.id=c.id))" +
                                "FROM CommentQuestion c where c.question.id=:id")
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Optional<QuestionDto> getQuestionDtoDaoById(Long id, Long userId) {

        TypedQuery<QuestionDto> dto = entityManager.createQuery(
                "select" +
                        " q.id as question_id," +
                        " q.title," +
                        " q.user.id as author_id," +
                        " q.user.fullName," +
                        " q.user.imageLink," +
                        " q.description," +
                        " q.persistDateTime," +
                        " q.lastUpdateDateTime," +
                        " (select sum(r.count) from Reputation r where r.author.id = q.user.id) as author_reputation," +
                        " (select count(a.id) from Answer a WHERE a.question.id = q.id) as count_answer," +
                        " (select sum(case when v.vote = 'UP_VOTE' then 1 else -1 end) from VoteQuestion v where v.question.id = q.id) as count_valuable," +
                        " (select v.vote from VoteQuestion v where v.question.id = q.id and v.user.id = :userId) as is_user_vote," +
                        " (select count(bm.id) > 0 from BookMarks bm where bm.question.id = q.id and bm.user.id = :userId) as is_user_bookmark," +
                        " (select count(qv.id) from QuestionViewed qv where qv.question.id = q.id) as view_count" +
                        " from Question q where q.id = :id and q.isDeleted = false")

                .setParameter("id", id)
                .setParameter("userId", userId)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionDtoResultTransformer());
        return SingleResultUtil.getSingleResultOrNull(dto);
    }
}

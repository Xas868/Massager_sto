package com.javamentor.qa.platform.dao.impl.pagination;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.dao.impl.pagination.transformer.QuestionPageDtoResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoByTagId")
public class QuestionPageDtoDaoByTagId implements PageDtoDao<QuestionViewDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionViewDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return (List<QuestionViewDto>) entityManager.createQuery(
                        "select" +
                                " q.id as question_id," +
                                " q.title," +
                                " q.user.id as author_id," +
                                " q.user.fullName," +
                                " q.user.imageLink," +
                                " q.description," +
                                " q.persistDateTime," +
                                " q.lastUpdateDateTime," +
                                " (coalesce((select sum(r.count) from Reputation r where r.author.id = q.user.id),0)) as author_reputation," +
                                " (coalesce((select count(a.id) from Answer a where a.question.id = q.id),0)) as answerCounter, " +
                                " (coalesce((select sum(case when v.vote = 'UP_VOTE' then 1 else -1 end) from VoteQuestion v where v.question.id = q.id), 0)) as count_valuable," +
                                " false as is_user_bookmark," +//" (select count(bm.id) > 0 from BookMarks bm where bm.question.id = q.id and bm.user.id = :userId) as is_user_bookmark, " +
                                " (coalesce((select count(qv.id) from QuestionViewed qv where qv.question.id = q.id), 0)) as view_count" +
                                " from Question q " +
                                " WHERE q.id IN (select q1.id from Question q1 join q1.tags t WHERE t.id = :id)  " +
                                " and :dateFilter = 0  OR q.persistDateTime >= current_date - :dateFilter " +
                                " ORDER BY q.persistDateTime desc"
                                /*"from Question q " +
                                "JOIN q.user u on q.user.id = u.id " +
                                "join Answer a ON a.question.id = q.id " +
                                "JOIN VoteQuestion v  ON v.question.id = q.id " +
                                "WHERE q.id IN (select q1.id from Question q1 join q1.tags t WHERE t.id = :id)  " +
                                " and :dateFilter = 0  OR q.persistDateTime >= current_date - :dateFilter " +
                                "group by q.id, q.persistDateTime, u.id  " +
                                "order by q.persistDateTime desc"*/
                )
                .setParameter("id", properties.getProps().get("id"))
                .setParameter("dateFilter", properties.getProps().get("dateFilter"))
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionPageDtoResultTransformer())
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("select count (q.id) from Question q Join q.tags t WHERE t.id = :id")
                .setParameter("id", properties.get("id"))
                .getSingleResult();
    }

}

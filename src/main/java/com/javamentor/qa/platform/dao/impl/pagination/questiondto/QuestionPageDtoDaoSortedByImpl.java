package com.javamentor.qa.platform.dao.impl.pagination.questiondto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.dao.impl.pagination.transformer.QuestionPageDtoResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.QuestionViewSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoSortedByImpl")
public class QuestionPageDtoDaoSortedByImpl implements PageDtoDao<QuestionViewDto> {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<QuestionViewDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        QuestionViewSort sortBy = (QuestionViewSort) properties.getProps().get("sortedBy");
        String select = "SELECT ";

        if (sortBy.equals(QuestionViewSort.NoAnswer)) {
            select = select + "DISTINCT";
        }

        return (List<QuestionViewDto>) entityManager.createQuery(select +
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
                                                                 " false as is_user_bookmark," +
                                                                 " (coalesce((select count(qv.id) from QuestionViewed qv where qv.question.id = q.id), 0)) as view_count "
                                                                 + sortBy.getComparingField())
                .setParameter("trackedTag", properties.getProps().get("trackedTag"))
                .setParameter("ignoredTag", properties.getProps().get("ignoredTag"))
                .setParameter("dateFilter", properties.getProps().get("dateFilter"))
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new QuestionPageDtoResultTransformer())
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery(
                        "select distinct" +
                        " count (distinct q.id)" +
                        " from Question q " +
                        " where ((:trackedTag) is null or q.id in (select q.id from Question q1 JOIN q1.tags t where q.id = q1.id and (t.id in (:trackedTag))))" +
                        " and q.id not in (select q.id from Question q1 JOIN q1.tags t where q.id = q1.id and t.id in (:ignoredTag) )" +
                        " and (:dateFilter = 0  OR q.persistDateTime >= current_date - :dateFilter) "
                )
                .setParameter("trackedTag", properties.get("trackedTag"))
                .setParameter("ignoredTag", properties.get("ignoredTag"))
                .setParameter("dateFilter", properties.get("dateFilter"))
                .getSingleResult();
    }
}

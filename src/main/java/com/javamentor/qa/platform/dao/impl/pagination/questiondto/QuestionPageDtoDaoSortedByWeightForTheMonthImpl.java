package com.javamentor.qa.platform.dao.impl.pagination.questiondto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.dao.impl.pagination.transformer.QuestionPageDtoResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("QuestionPageDtoDaoSortedByWeightForTheMonthImpl")
public class QuestionPageDtoDaoSortedByWeightForTheMonthImpl implements PageDtoDao<QuestionViewDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuestionViewDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager.createQuery(
                "select " +
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
                        " from Question q" +
                        " where ((:trackedTags) IS NULL OR q.id IN (select q.id from Question q join q.tags t where t.id in (:trackedTags))) " +
                        " and ((:ignoredTags) IS NULL OR q.id not IN (select q.id from Question q join q.tags t where t.id in (:ignoredTags)))" +
                        " and q.persistDateTime > date_trunc('month', current_timestamp) " +
                        " order by answerCounter desc, count_valuable desc, view_count desc"
                )
                .setParameter("trackedTags",properties.getProps().get("trackedTags"))
                .setParameter("ignoredTags",properties.getProps().get("ignoredTags"))
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionPageDtoResultTransformer())
                .getResultList();

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("select distinct count(distinct q.id) from Question q  " +
                        "where ((:trackedTags) is null " +
                        "or q.id in " +
                        "(select q.id from Question q join q.tags t where t.id in (:trackedTags))) and" +
                        "((:ignoredTags) is null " +
                        "or q.id not in " +
                        "(select q.id from Question q join q.tags t where t.id in (:ignoredTags))) and" +
                        " q.persistDateTime > date_trunc('month', current_timestamp)")
                .setParameter("trackedTags",properties.get("trackedTags"))
                .setParameter("ignoredTags",properties.get("ignoredTags"))
                .getSingleResult();
    }
}

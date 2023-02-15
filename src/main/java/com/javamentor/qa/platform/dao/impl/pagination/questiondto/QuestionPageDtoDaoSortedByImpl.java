package com.javamentor.qa.platform.dao.impl.pagination.questiondto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.dao.impl.pagination.transformer.QuestionPageDtoResultTransformer;
import com.javamentor.qa.platform.models.dto.QuestionViewDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.QuestionViewSort;
import com.javamentor.qa.platform.models.entity.question.TrackedTag;
import com.javamentor.qa.platform.models.entity.user.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("QuestionPageDtoDaoSortedByImpl")
public class QuestionPageDtoDaoSortedByImpl implements PageDtoDao<QuestionViewDto> {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<QuestionViewDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        QuestionViewSort sortBy = (QuestionViewSort) properties.getProps().get("sortedBy");
        User user = (User) properties.getProps().get("user");

        return (List<QuestionViewDto>) entityManager.createQuery(
                        "select DISTINCT" +
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
                        " (coalesce((select count(qv.id) from QuestionViewed qv where qv.question.id = q.id), 0)) as view_count " +
                        " from Question q " +
                        " left JOIN q.tags t " +
                        " left JOIN TrackedTag trTag ON trTag.user.id = :userId " +
                        " left JOIN IgnoredTag ignored ON ignored.user.id = :userId " +
                        " WHERE ((trTag.trackedTag.id) IS NULL OR t.id IN (SELECT trTag.trackedTag.id FROM trTag WHERE trTag.user.id = :userId)) " +
                        " AND ((ignored.ignoredTag.id) IS NULL OR q.id not IN " +
                        " (SELECT q.id from Question q join q.tags t where t.id in (SELECT ig.ignoredTag.id FROM IgnoredTag ig WHERE ig.user.id = :userId))) " +
                        " AND (:dateFilter = 0 or q.persistDateTime >= current_date - :dateFilter) " +
                        sortBy.getSortClause()
                )
                .setParameter("dateFilter", properties.getProps().get("dateFilter"))
                .setParameter("userId", user.getId())
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new QuestionPageDtoResultTransformer())
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        User user = (User) properties.get("user");

        return (Long) entityManager.createQuery(
                        "select distinct" +
                        " count (distinct q.id)" +
                        " from Question q " +
                        " left JOIN q.tags t " +
                        " left JOIN TrackedTag trTag ON trTag.user.id = :userId " +
                        " left JOIN IgnoredTag ignored ON ignored.user.id = :userId " +
                        " WHERE ((trTag.trackedTag.id) IS NULL OR t.id IN " +
                        " (SELECT trTag.trackedTag.id FROM trTag WHERE trTag.user.id = :userId)) " +
                        " AND ((ignored.ignoredTag.id) IS NULL OR q.id not IN " +
                        " (SELECT q.id from Question q join q.tags t where t.id in (SELECT ig.ignoredTag.id FROM IgnoredTag ig WHERE ig.user.id = :userId))) " +
                        " AND (:dateFilter = 0 or q.persistDateTime >= current_date - :dateFilter)"
                )
                .setParameter("userId", user.getId())
                .setParameter("dateFilter", properties.get("dateFilter"))
                .getSingleResult();
    }
}

package com.javamentor.qa.platform.dao.impl.pagination.user.profile;


import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.ProfileQuestionSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;


@Repository("UserProfileQuestionsPageDtoDaoImpl")
public class UserProfileQuestionsPageDtoDaoImpl implements PageDtoDao<UserProfileQuestionDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserProfileQuestionDto> getPaginationItems(PaginationData properties) {

        int itemsOnPage = properties.getItemsOnPage();
        ProfileQuestionSort profileQuestionSort = (ProfileQuestionSort) properties.getProps().get("sorted");
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        List<UserProfileQuestionDto> resultList = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileQuestionDto(" +
                        "q.id,q.title," +
                        "coalesce((select count(a.id) from Answer a where a.question.id=q.id),0),q.persistDateTime," +
                        "(select count (qv.question.id) from QuestionViewed qv where qv.question.id = q.id) as qView," +
                        "(select coalesce(sum(case when vq.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteQuestion vq where vq.question.id = q.id) as qVote)" +
                        "from Question q where q.user.id=:id order by " + profileQuestionSort.getComparingField(), UserProfileQuestionDto.class)
                .setParameter("id", properties.getProps().get("userId"))
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
        return resultList;

    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) entityManager.createQuery("select count (qa) from Question qa where qa.user.id = :id")
                .setParameter("id", properties.get("userId"))
                .getSingleResult();
    }
}
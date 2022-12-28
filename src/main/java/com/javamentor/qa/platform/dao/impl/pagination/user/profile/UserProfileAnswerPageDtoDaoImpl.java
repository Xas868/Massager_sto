package com.javamentor.qa.platform.dao.impl.pagination.user.profile;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.answer.ProfileAnswerSort;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("UserProfileAnswerPageDtoDaoImpl")
public class UserProfileAnswerPageDtoDaoImpl implements PageDtoDao<UserProfileAnswerDto>{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserProfileAnswerDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        User user = (User) properties.getProps().get("user");
        ProfileAnswerSort profileAnswerSort = (ProfileAnswerSort) properties.getProps().get("profileAnswerSort");

        List<UserProfileAnswerDto> id = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileAnswerDto(" +
                        "a.id," +
                        "a.question.title," +
                        "(select count (qv) from QuestionViewed qv where a.question.id = qv.question.id)," +
                        "(select coalesce(sum(case when va.vote = 'UP_VOTE' then 1 else -1 end), 0) from VoteAnswer va where va.answer.id = a.id ) as sVote," +
                        "a.question.id, a.persistDateTime)" +
                        "from Answer a where a.user.id=:id order by " + profileAnswerSort.getComparingField(), UserProfileAnswerDto.class)
                .setParameter("id", user.getId())
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
        return id;
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) entityManager.createQuery("select count (a) from Answer a where a.user.id = :id")
                .setParameter("id", properties.get("userId"))
                .getSingleResult();
    }
    @Override
    public String toString() {
        return getClass().getName();
    }
}

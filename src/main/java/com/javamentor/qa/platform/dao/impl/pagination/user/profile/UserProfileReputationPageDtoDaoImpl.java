package com.javamentor.qa.platform.dao.impl.pagination.user.profile;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserProfileReputationDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.ProfileReputationSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("UserProfileReputationPageDtoDaoImpl")
public class UserProfileReputationPageDtoDaoImpl implements PageDtoDao<UserProfileReputationDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserProfileReputationDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        ProfileReputationSort profileReputationSort = (ProfileReputationSort) properties.getProps().get("profileReputationSort");

        List<UserProfileReputationDto> id = entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileReputationDto(" +
                        "a.count,"+
                        "a.question.id,"+
                        "(select va.title from Question va where va.id = a.question.id)," +
                        "a.answer.id, a.persistDate, a.type)" +
                        "from Reputation a where a.author.id =: id  order by " +
                        profileReputationSort.ComparingField(), UserProfileReputationDto.class)
                .setParameter("id", properties.getProps().get("userId"))
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
        return id;
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) entityManager.createQuery("select count (a) from Reputation a where a.author.id = :id")
                .setParameter("id", properties.get("userId"))
                .getSingleResult();
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
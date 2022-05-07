package com.javamentor.qa.platform.dao.impl.pagination.userdto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("UserPageDtoDaoAllUsersByRepImpl")
public class UserPageDtoDaoAllUsersByRepImpl implements PageDtoDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;
    private String filter;

    @Override
    public List<UserDto> getPaginationItems(PaginationData properties) {
        filter = properties.getFilter();
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        String hql = "select new com.javamentor.qa.platform.models.dto.UserDto" +
                "(u.id, u.email, u.fullName, u.imageLink, u.city, " +
                "SUM(COALESCE(r.count, 0)) AS reputation)" +
                "from User u left join Reputation r ON r.author.id=u.id " +
                "where u.isDeleted = false ";

        if (filter != null) {
            hql += "and (upper(u.nickname) like upper(:filter) " +
                    "or upper(u.email) like upper(:filter) " +
                    "or upper(u.fullName) like upper(:filter)) " +
                    "group by u.id order by reputation desc";
            return entityManager.createQuery(hql, UserDto.class)
                    .setParameter("filter", "%" + filter + "%")
                    .setFirstResult(offset)
                    .setMaxResults(itemsOnPage)
                    .getResultList();
        }

        hql += "group by u.id order by reputation desc";
        return entityManager.createQuery(hql, UserDto.class)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        if (filter != null) {
            return (Long) entityManager
                    .createQuery("select count(u.id) from User u " +
                            "where u.isDeleted = false " +
                            "and (upper(u.nickname) like upper(:filter) " +
                            "or upper(u.email) like upper(:filter) " +
                            "or upper(u.fullName) like upper(:filter))")
                    .setParameter("filter", "%" + filter + "%")
                    .getSingleResult();
        }
        return (Long) entityManager.createQuery("select count(u.id) from User u where u.isDeleted = false ")
                .getSingleResult();
    }
}

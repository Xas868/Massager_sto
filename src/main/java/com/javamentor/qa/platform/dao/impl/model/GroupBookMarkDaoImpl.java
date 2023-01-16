package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookMarkDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.dto.UserProfileGroup;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupBookMarkDaoImpl extends ReadWriteDaoImpl<GroupBookmark, Long> implements GroupBookMarkDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserProfileGroup> getAllUserBookMarkGroupNamesByUserId(Long id) {
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.UserProfileGroup (gb.id, gb.title) " +
                        "from GroupBookmark gb where gb.user.id =: id", UserProfileGroup.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public boolean isGroupBookMarkExistsByName(Long id, String title) {
        Long singleResult = entityManager.createQuery("select count (gb) from GroupBookmark gb where gb.user.id = :id and gb.title = :title", Long.class)
                .setParameter("id", id)
                .setParameter("title", title)
                .getSingleResult();
        return singleResult > 0;
    }
}
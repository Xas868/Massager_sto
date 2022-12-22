package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookMarkDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupBookMarkDaoImpl extends ReadWriteDaoImpl<GroupBookmark, Long> implements GroupBookMarkDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> getAllUserBookMarkGroupNamesByUserId(Long id) {
        return entityManager.createQuery("select gb.title from GroupBookmark gb where gb.user.id = :id", String.class)
                .setParameter("id", id)
                .getResultList();
    }
}
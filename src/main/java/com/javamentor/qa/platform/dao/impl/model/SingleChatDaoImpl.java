package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SingleChatDaoImpl extends ReadWriteDaoImpl<SingleChat, Long> implements SingleChatDao {

    @PersistenceContext
    public EntityManager entityManager;

    public void deleteById(Long id, Long userId){
        entityManager
                .createQuery("update SingleChat s set s.userOne = case when s.userOne = :userId then nullif(s.userOne, :userId) else s.userOne end," +
                           "s.useTwo = case when s.useTwo = :userId then nullif(s.useTwo,:userId) else s.useTwo end where s.id = :id" )
                .setParameter("userId", userId)
                .setParameter("id", id)
                .executeUpdate();
    }
}

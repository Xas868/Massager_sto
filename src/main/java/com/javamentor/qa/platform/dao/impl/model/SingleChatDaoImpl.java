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
                .createQuery("update SingleChat s set s.userOneIsDeleted = case when s.userOne.id = :userId then true else s.userOneIsDeleted end," +
                           "s.userTwoIsDeleted = case when s.useTwo.id = :userId then true else s.userTwoIsDeleted end where s.id = :id" )
                .setParameter("userId", userId)
                .setParameter("id", id)
                .executeUpdate();
    }
}

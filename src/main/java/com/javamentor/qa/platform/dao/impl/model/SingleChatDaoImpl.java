package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class SingleChatDaoImpl extends ReadWriteDaoImpl<SingleChat, Long> implements SingleChatDao {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void deleteUserFromSingleChatById(Long id, Long userId){
        entityManager
                .createQuery("update SingleChat s set s.userOneIsDeleted = case when s.userOne.id = :userId then true else s.userOneIsDeleted end," +
                           "s.userTwoIsDeleted = case when s.useTwo.id = :userId then true else s.userTwoIsDeleted end where s.id = :id" )
                .setParameter("userId", userId)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public long findChatForId (Long userOne,Long userTwo){
        Long res;
        String query ="select COALESCE (id,0) from SingleChat where (userOne.id=%s AND useTwo.id=%s) OR (userOne.id=%s AND useTwo.id=%s)".formatted(userTwo.toString(), userOne.toString(), userOne.toString(), userTwo.toString());
        try {
            res = (Long)entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e){
            res=0L;
        }
        return res;
    }
    @Override
    public void deleteSinglChat (Long id) {
        String query ="delete from SingleChat a where a.id=%s".formatted(id.toString());
        entityManager.createQuery(query).executeUpdate();
    }

}

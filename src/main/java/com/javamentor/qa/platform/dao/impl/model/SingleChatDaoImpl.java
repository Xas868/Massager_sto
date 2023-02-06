package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;

@Repository
public class SingleChatDaoImpl extends ReadWriteDaoImpl<SingleChat, Long> implements SingleChatDao {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void deleteUserFromSingleChatById(Long id, Long userId) {
        entityManager
                .createQuery("update SingleChat s set s.userOneIsDeleted = case when s.userOne.id = :userId then true else s.userOneIsDeleted end," +
                        "s.userTwoIsDeleted = case when s.useTwo.id = :userId then true else s.userTwoIsDeleted end where s.id = :id")
                .setParameter("userId", userId)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public long getChatForId(Long userOneId, Long userTwoId) {
        TypedQuery<Long> query = (TypedQuery<Long>) entityManager.createQuery("select COALESCE (r.id,0) as s from SingleChat r where (r.userOne.id=:id AND r.useTwo.id=:id1) OR (r.userOne.id=:id1 AND r.useTwo.id=:id)")
                .setParameter("id", userOneId)
                .setParameter("id1", userTwoId);
        Long res;
        Optional<Long> bufer = SingleResultUtil.getSingleResultOrNull(query);
        if (bufer.isEmpty()) {
            res = Long.valueOf(0);
        } else {
            res = bufer.get();
        }
        return res;
    }
    @Override
    public void deleteSinglChat(Long id) {
        entityManager.createQuery("delete from Message a where a.chat.id=:ids").setParameter("ids", id).executeUpdate();
        entityManager.createQuery("delete from SingleChat a where a.chat.id=:ids").setParameter("ids", id).executeUpdate();
        entityManager.createQuery("delete from Chat a where a.id=:ids").setParameter("ids", id).executeUpdate();
    }

}

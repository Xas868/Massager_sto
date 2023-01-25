package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.models.dto.UserBlockDto;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserBlockedResourceDaoImpl extends ReadWriteDaoImpl<UserDaoImpl,Long>{
    @PersistenceContext
    public EntityManager entityManager;


    public void deleteUserFromBlockById(Long id, Long userId) {
        entityManager
                .createNativeQuery("delete from groupchat_has_users where chat_id=:id and user_id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}

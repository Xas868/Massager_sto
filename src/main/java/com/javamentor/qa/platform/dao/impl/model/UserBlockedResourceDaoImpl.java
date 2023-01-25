package com.javamentor.qa.platform.dao.impl.model;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserBlockedResourceDaoImpl extends ReadWriteDaoImpl<UserDaoImpl,Long>{
    @PersistenceContext
    public EntityManager entityManager;


    public void deleteUserFromBlockById(Long profile, Long block) {
        entityManager
                .createNativeQuery("delete from block_chat_user_list where profile_id=:profile and blocked_id=:block")
                .setParameter("profile", profile)
                .setParameter("block", block)
                .executeUpdate();
    }
}

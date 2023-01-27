package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BlockChatUserListDao;
import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BlockChatUserListDaoImpl extends ReadWriteDaoImpl<BlockChatUserList, Long> implements BlockChatUserListDao {
    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void deleteUserFromBlockById(Long profile, Long blocked) {
        entityManager
                .createNativeQuery("delete from block_chat_user_list where profile_id=:profile and blocked_id=:blocked")
                .setParameter("profile", profile)
                .setParameter("blocked", blocked)
                .executeUpdate();
    }
}

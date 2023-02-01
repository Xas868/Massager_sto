package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BlockChatUserListDao;
import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    public Boolean findUserFromBlockById(Long profil, Long block){
        String query ="select count (blocked) from BlockChatUserList where profile=%s AND blocked=%s".formatted(profil.toString(), block.toString());
        return (long)entityManager.createQuery(query).getSingleResult()>0;
    }
}

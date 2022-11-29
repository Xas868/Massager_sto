package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupChatRoomDaoImpl extends ReadWriteDaoImpl<GroupChat, Long> implements GroupChatRoomDao {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void deleteUserFromGroupChatById(Long id, Long userId) {
        entityManager
                .createNativeQuery("delete from groupchat_has_users where chat_id=:id and user_id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public boolean isUserAuthor(Long id, Long userId) {
        return entityManager.createQuery("select case when (count(gc) > 0)  then true else false end from GroupChat gc where gc.id = :idChat and gc.userAuthor.id = :id", Boolean.class)
                .setParameter("id", userId)
                .setParameter("idChat", id)
                .getSingleResult();
    }

    @Override
    public Optional<GroupChat> getGroupChatAndUsers(long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery
                //return (entityManager.createQuery
                        ("select groupChat from GroupChat groupChat join fetch groupChat.chat join fetch groupChat.users where groupChat.id=:id", GroupChat.class)

                .setParameter("id", id));

    }


}





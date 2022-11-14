package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class GroupChatRoomDaoImpl extends ReadWriteDaoImpl<GroupChat, Long> implements GroupChatRoomDao {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public void deleteUserFromGroupChatById(Long id, Long userId){
        entityManager
                .createNativeQuery("delete from groupchat_has_users where chat_id=:id and user_id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public void deleteAllChat(Long id) {
        GroupChat gc = getGroupChatAndUsers(id).get();
        gc.setUsers(null);
        entityManager.createQuery("DELETE FROM GroupChat WHERE chat.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<GroupChat> getGroupChatAndUsers(long id) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery
                        ("select groupChat from GroupChat groupChat join fetch groupChat.chat join fetch groupChat.users where groupChat.id=:id", GroupChat.class)
                .setParameter("id", id));

    }

}

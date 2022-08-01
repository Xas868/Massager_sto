package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;

import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class GroupChatRoomDaoImpl extends ReadWriteDaoImpl<GroupChat, Long> implements GroupChatRoomDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GroupChat getGroupChatAndUsers(long id) {
        GroupChat groupChat = entityManager.createQuery
                        ("select groupChat from GroupChat groupChat join fetch groupChat.chat join fetch groupChat.users where groupChat.id=:id", GroupChat.class)
                .setParameter("id", id)
                .getSingleResult();

        return groupChat;
    }
}

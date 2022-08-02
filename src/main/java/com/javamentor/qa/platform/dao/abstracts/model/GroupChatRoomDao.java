package com.javamentor.qa.platform.dao.abstracts.model;


import com.javamentor.qa.platform.models.entity.chat.GroupChat;

import java.util.Optional;

public interface GroupChatRoomDao extends ReadWriteDao<GroupChat, Long> {
    Optional<GroupChat> getGroupChatAndUsers(long id);
}

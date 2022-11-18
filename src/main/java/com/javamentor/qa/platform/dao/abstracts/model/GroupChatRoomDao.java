package com.javamentor.qa.platform.dao.abstracts.model;


import com.javamentor.qa.platform.models.entity.chat.GroupChat;

import java.util.Optional;

public interface GroupChatRoomDao extends ReadWriteDao<GroupChat, Long> {

    void deleteUserFromGroupChatById(Long id, Long userId);

    void deleteAllUsersFromChat(Long chatId);

    boolean UserAuthor(Long userId);

    Optional<GroupChat> getGroupChatAndUsers(long id);
}

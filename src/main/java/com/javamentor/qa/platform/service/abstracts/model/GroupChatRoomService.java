package com.javamentor.qa.platform.service.abstracts.model;


import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface GroupChatRoomService extends ReadWriteService<GroupChat, Long> {

    Optional<GroupChat> getGroupChatAndUsers(long id);

    @Transactional
    void deleteUserFromGroupChatById(Long chatId, Long userId);
}

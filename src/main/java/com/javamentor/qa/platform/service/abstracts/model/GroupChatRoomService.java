package com.javamentor.qa.platform.service.abstracts.model;


import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.transaction.annotation.Transactional;


public interface GroupChatRoomService  extends ReadWriteService<GroupChat, Long> {

    @Transactional
    void deleteUserFromGroupChatById(Long chatId, Long userId);
}

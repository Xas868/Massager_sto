package com.javamentor.qa.platform.groupchat.websockets.chatroom;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;

import java.util.Optional;

public interface ChatRoomDao  extends ReadWriteDao<Chat, Long> {

    Optional<Chat> findIdChatByUser_Sender(Long userSender);
}

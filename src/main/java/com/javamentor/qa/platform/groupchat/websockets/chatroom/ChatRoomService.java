package com.javamentor.qa.platform.groupchat.websockets.chatroom;

import com.javamentor.qa.platform.models.entity.chat.Chat;

import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

import java.util.Optional;

public interface ChatRoomService extends ReadWriteService<Chat, Long> {

    Optional<Long> findIdChatByUser_Sender(Long userSender,boolean createIfNotExist);

}

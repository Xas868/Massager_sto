package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.CreateSingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface SingleChatRoomService extends ReadWriteService<SingleChat, Long> {
    SingleChat createSingleChatAndFirstMessage(String stringMessage, User currentUser, Optional<User> destinationUser);
}

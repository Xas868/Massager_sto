package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;

public interface SingleChatRoomService extends ReadWriteService<SingleChat, Long> {
    SingleChatDto createSingleChatDto(User currentUser, User destinationUser, String message);
}

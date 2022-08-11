package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.SingleChat;

public interface SingleChatRoomService extends ReadWriteService<SingleChat, Long> {
    SingleChat createSingleChatAndFirstMessage(String stringMessage, Long id);
}

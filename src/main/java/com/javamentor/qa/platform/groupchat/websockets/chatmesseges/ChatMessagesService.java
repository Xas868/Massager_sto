package com.javamentor.qa.platform.groupchat.websockets.chatmesseges;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public interface ChatMessagesService   extends ReadWriteService<Message, Long> {
}

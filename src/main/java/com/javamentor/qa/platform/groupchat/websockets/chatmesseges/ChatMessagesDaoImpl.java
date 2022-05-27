package com.javamentor.qa.platform.groupchat.websockets.chatmesseges;

import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.entity.chat.Message;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessagesDaoImpl extends ReadWriteDaoImpl<Message, Long> implements ChatMessagesDao {
}

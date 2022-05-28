package com.javamentor.qa.platform.groupchat.websockets.chatmesseges;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Message;

public interface ChatMessagesDao extends ReadWriteDao<Message, Long> {


}

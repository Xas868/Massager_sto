package com.javamentor.qa.platform.groupchat.websockets.chatmesseges;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ChatMessagesServiceImpl  extends ReadWriteServiceImpl<Message, Long> implements ChatMessagesService {

    public ChatMessagesServiceImpl(ReadWriteDao<Message, Long> readWriteDao) {
        super(readWriteDao);
    }
}

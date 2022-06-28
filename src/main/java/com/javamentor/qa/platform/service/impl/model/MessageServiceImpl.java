package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.model.GroupChatDao;
import com.javamentor.qa.platform.dao.impl.model.model.MessageDao;
import com.javamentor.qa.platform.dao.impl.model.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.service.abstracts.model.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ReadWriteServiceImpl<Message,Long> implements MessageService {
    private final MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        super(messageDao);
        this.messageDao = messageDao;
    }
}

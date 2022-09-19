package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.MessageStarDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import com.javamentor.qa.platform.service.abstracts.model.MessageStarService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageStarServiceImpl extends ReadWriteServiceImpl<MessageStar, Long> implements MessageStarService {
    private final MessageStarDao messageStarDao;
    public MessageStarServiceImpl(MessageStarDao messageStarDao) {
        super(messageStarDao);
        this.messageStarDao = messageStarDao;
    }

    @Override
    public Optional<MessageStar> getMessageStarByUserAndMessage(Long userId, Long messageId) {
        return messageStarDao.getMessageStarByUserAndMessage(userId, messageId);
    }
}

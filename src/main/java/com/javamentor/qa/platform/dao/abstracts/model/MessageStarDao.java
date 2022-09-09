package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.MessageStar;

import java.util.Optional;

public interface MessageStarDao extends ReadWriteDao<MessageStar, Long> {
    Optional<MessageStar> getMessageStarByUserAndMessage(Long userId, Long messageId);
}

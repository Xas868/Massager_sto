package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.MessageStar;

import java.util.Optional;

public interface MessageStarService extends ReadWriteService<MessageStar, Long> {
    Optional<MessageStar> getMessageStarByUserAndMessage(Long userId, Long messageId);
}

package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.springframework.transaction.annotation.Transactional;

public interface SingleChatService extends ReadWriteService<SingleChat, Long>{
    @Transactional
    void deleteUserFromSingleChatById(Long chatId, Long userId);

    SingleChat createSingleChatAndFirstMessage(String stringMessage, SingleChat singleChat) throws Exception;
}

package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.SingleChat;

public interface SingleChatDao extends ReadWriteDao<SingleChat, Long>{
    void deleteUserFromSingleChatById(Long id, Long userId);
    long findChatForId (Long userOne, Long userTwo);
    void deleteSinglChat (Long id);
}

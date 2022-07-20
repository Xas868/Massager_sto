package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingleChatServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatService {

    private final SingleChatDao singleChatDao;

    public SingleChatServiceImpl(SingleChatDao singleChatDao) {
        super(singleChatDao);
        this.singleChatDao = singleChatDao;
    }

    @Override
    @Transactional
    public void deleteUserFromSingleChatById(Long chatId, Long userId) {
        singleChatDao.deleteUserFromSingleChatById(chatId, userId);
    }
}

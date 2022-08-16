package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import com.javamentor.qa.platform.webapp.controllers.exceptions.UserRemovedFromTheSingleChat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

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
        SingleChat singleChat = singleChatDao.getById(chatId)
                .orElseThrow(() ->new EntityNotFoundException("Single chat not found"));

        if (Objects.equals(singleChat.getUserOne().getId(), userId) && singleChat.getUserOneIsDeleted()){
             throw new UserRemovedFromTheSingleChat("First user has already been removed from the chat");
        }
        if (Objects.equals(singleChat.getUseTwo().getId(), userId) && singleChat.getUserTwoIsDeleted()){
            throw new UserRemovedFromTheSingleChat("Second user has already been removed from the chat");
        }

        singleChatDao.deleteUserFromSingleChatById(chatId, userId);
    }
}

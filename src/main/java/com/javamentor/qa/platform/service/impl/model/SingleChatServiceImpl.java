package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.MessageService;
import com.javamentor.qa.platform.service.abstracts.model.SingleChatService;
import com.javamentor.qa.platform.webapp.controllers.exceptions.UserRemovedFromTheSingleChat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
public class SingleChatServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatService {

    private final SingleChatDao singleChatDao;
    private final MessageService messageService;

    public SingleChatServiceImpl(SingleChatDao singleChatDao, MessageService messageService) {
        super(singleChatDao);
        this.singleChatDao = singleChatDao;
        this.messageService = messageService;
    }

    @Override
    @Transactional
    public void deleteUserFromSingleChatById(Long chatId, Long userId) {
        SingleChat singleChat = singleChatDao.getById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Single chat not found"));

        if (Objects.equals(singleChat.getUserOne().getId(), userId) && singleChat.getUserOneIsDeleted()) {
            throw new UserRemovedFromTheSingleChat("First user has already been removed from the chat");
        }
        if (Objects.equals(singleChat.getUseTwo().getId(), userId) && singleChat.getUserTwoIsDeleted()) {
            throw new UserRemovedFromTheSingleChat("Second user has already been removed from the chat");
        }

        singleChatDao.deleteUserFromSingleChatById(chatId, userId);
    }

    @Transactional
    public SingleChat createSingleChatAndFirstMessage(String stringMessage, SingleChat singleChat) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        singleChat = SingleChat.builder()
                .chat(new Chat(ChatType.SINGLE))
                .userOne(currentUser)
                .useTwo(singleChat.getUseTwo())
                .userOneIsDeleted(false)
                .userTwoIsDeleted(false)
                .build();
        persist(singleChat);
        Message message = Message.builder()
                .message(stringMessage)
                .userSender(singleChat.getUserOne())
                .chat(singleChat.getChat())
                .build();
        messageService.persist(message);
        return singleChat;
    }

    @Override
    public long getChatForId(Long userOneId, Long userTwoId) {
        return singleChatDao.getChatForId(userOneId, userTwoId);
    }
}

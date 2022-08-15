package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.*;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.*;
import com.javamentor.qa.platform.webapp.converters.SingleChatConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingleChatRoomServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatRoomService {

    private final SingleChatService singleChatService;
    private final MessageService messageService;
    private final SingleChatConverter singleChatConverter;

    public SingleChatRoomServiceImpl(@Qualifier("singleChatRoomDaoImpl") ReadWriteDao<SingleChat, Long> readWriteDao, SingleChatService singleChatService, MessageService messageService, SingleChatConverter singleChatConverter) {
        super(readWriteDao);
        this.singleChatService = singleChatService;
        this.messageService = messageService;
        this.singleChatConverter = singleChatConverter;
    }

    @Transactional
    public SingleChat createSingleChatAndFirstMessage(String stringMessage, SingleChat singleChat) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        singleChat = SingleChat.builder()
                    .chat(new Chat(ChatType.SINGLE))
                    .userOne(currentUser)
                    .useTwo(singleChat.getUseTwo())
                    .build();
        singleChatService.persist(singleChat);
        Message message = Message.builder()
                .message(stringMessage)
                .userSender(singleChat.getUserOne())
                .chat(singleChat.getChat())
                .build();
        messageService.persist(message);
        return singleChat;
    }
}

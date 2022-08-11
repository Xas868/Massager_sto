package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.*;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SingleChatRoomServiceImpl extends ReadWriteServiceImpl<SingleChat, Long> implements SingleChatRoomService {

    private final SingleChatService singleChatService;
    private final MessageService messageService;
    private final UserService userService;

    public SingleChatRoomServiceImpl(@Qualifier("singleChatRoomDaoImpl") ReadWriteDao<SingleChat, Long> readWriteDao, SingleChatService singleChatService, MessageService messageService, UserService userService) {
        super(readWriteDao);
        this.singleChatService = singleChatService;
        this.messageService = messageService;
        this.userService = userService;
    }

    @Transactional
    public SingleChat createSingleChatAndFirstMessage(String stringMessage, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        Optional<User> destinationUser = userService.getById(id);
        SingleChat singleChat;
        try {
            singleChat = SingleChat.builder()
                    .chat(new Chat(ChatType.SINGLE))
                    .userOne(currentUser)
                    .useTwo(destinationUser.orElseThrow())
                    .build();
        } catch (Exception ex) {
            throw new NullPointerException("User not found!");
        }
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

package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.service.abstracts.model.ChatMessagesService;
import com.javamentor.qa.platform.service.abstracts.model.ChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;


@Controller
public class WebSocketBroadcastController {



    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatMessagesService chatMessagesService;
    @Autowired
    UserService userService;
    @Autowired
    GroupChatRoomService groupChatRoomService;



    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    @Transactional
    public MessageCreateDtoRequest send(@Payload MessageCreateDtoRequest messageRequestDto) {
        Message message = new Message();
        Chat chat = new Chat();
        chat.setChatType(ChatType.GROUP);
        chat.setTitle("gr");
        chat.setId(1L);
        GroupChat groupChat = GroupChat.builder().id(1L).chat(chat).build();
        chatRoomService.update(chat);
        groupChatRoomService.update(groupChat);

        message.setMessage(messageRequestDto.getMessage());
        message.setChat(chat);
        message.setUserSender(userById(messageRequestDto.getSenderId()));
        message.setPersistDate(LocalDateTime.now());
        chatMessagesService.persist(message);

        MessageCreateDtoRequest dtoRequestNew = new MessageCreateDtoRequest(messageRequestDto.getMessage()
                , messageRequestDto.getSenderId()
                , message.getPersistDate()
                , messageRequestDto.getChatId()
                , messageRequestDto.getSenderNickname()
                , userById(messageRequestDto.getSenderId()).getImageLink());

        return dtoRequestNew;

    }

    User userById(Long id) {
        return userService.getById(id).orElse(null);
    }

}

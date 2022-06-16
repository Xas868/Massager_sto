package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.service.abstracts.model.ChatMessagesService;
import com.javamentor.qa.platform.webapp.converters.MessagesForGroupChatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


@Controller
public class WebSocketBroadcastController {
    @Autowired
    private MessagesForGroupChatConverter messagesForGroupChatConverter;


//    @Autowired
//    private GroupChatRoomService groupChatRoomService; - его зависимость


    @Autowired
    private ChatMessagesService chatMessagesService;


    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    @Transactional
    public MessageCreateDtoRequest send(@Payload MessageCreateDtoRequest messageRequestDto) {


//     для проверки работы чата -    groupChatRoomService.persist(groupChatRoomService.getById(messageRequestDto.getChatId()).orElse(new GroupChat()));
        chatMessagesService.persist(messagesForGroupChatConverter.MessageDtoToMessage(messageRequestDto));
        return messagesForGroupChatConverter.MessageToMessageDto(messagesForGroupChatConverter.MessageDtoToMessage(messageRequestDto));


    }


}

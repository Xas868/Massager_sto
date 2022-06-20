package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.models.dto.MessageCreateDtoResponse;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.service.abstracts.model.MessageService;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.webapp.converters.MessagesForGroupChatConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


@Controller
public class WebSocketBroadcastController {

    private final MessagesForGroupChatConverter messagesForGroupChatConverter;
    private final MessageService messageService;

    @Autowired
    public WebSocketBroadcastController(MessagesForGroupChatConverter messagesForGroupChatConverter, MessageService messageService) {
        this.messagesForGroupChatConverter = messagesForGroupChatConverter;
        this.messageService = messageService;
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    @Transactional
    public MessageCreateDtoResponse send(@Payload MessageCreateDtoRequest messageRequestDto) {




        messageService.persist(messagesForGroupChatConverter.MessageDtoToMessage(messageRequestDto));
        return messagesForGroupChatConverter.MessageToMessageDto(messagesForGroupChatConverter.MessageDtoToMessage(messageRequestDto));


    }


}

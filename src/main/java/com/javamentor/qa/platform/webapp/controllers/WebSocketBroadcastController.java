package com.javamentor.qa.platform.webapp.controllers;

import com.javamentor.qa.platform.models.dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.service.abstracts.model.ChatMessagesService;
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
    private final GroupChatRoomService groupChatRoomService;
    private final ChatMessagesService chatMessagesService;

    @Autowired
    public WebSocketBroadcastController(MessagesForGroupChatConverter messagesForGroupChatConverter, GroupChatRoomService groupChatRoomService, ChatMessagesService chatMessagesService) {
        this.messagesForGroupChatConverter = messagesForGroupChatConverter;
        this.groupChatRoomService = groupChatRoomService;
        this.chatMessagesService = chatMessagesService;
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    @Transactional
    public MessageCreateDtoRequest send(@Payload MessageCreateDtoRequest messageRequestDto) {


        groupChatRoomService.persist(groupChatRoomService.getById(messageRequestDto.getChatId()).orElse(new GroupChat()));
        chatMessagesService.persist(messagesForGroupChatConverter.MessageDtoToMessage(messageRequestDto));
        return messagesForGroupChatConverter.MessageToMessageDto(messagesForGroupChatConverter.MessageDtoToMessage(messageRequestDto));


    }


}

package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.models.entity.chat.Chat;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ChatRoomService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
@Component
public abstract class MessagesForGroupChatConverter {
    @Autowired
    UserService userService;
    @Autowired
    ChatRoomService chatRoomService;


    @Mapping(source = "messageDto.message", target = "message")
    @Mapping(source = "messageDto.chatId", target = "chat", qualifiedByName = "getChatById")
    @Mapping(ignore = true, target = "persistDate", expression = "java( new java.time.LocalDateTime())")
    @Mapping(source = "messageDto.senderId", target = "userSender", qualifiedByName = "idToUser")
    public abstract Message MessageDtoToMessage(MessageCreateDtoRequest messageDto);

    @Named("idToUser")
    User getuserById(Long id) {
        return userService.getById(id).orElse(null);

    }

    @Named("getChatById")
    Chat chatById(Long id) {
        return chatRoomService.getById(id).orElse(null);
    }

    @Named("getTime")
    MessageCreateDtoRequest getTime(MessageCreateDtoRequest messageDto) {
        messageDto.setTime(LocalDateTime.now());
        return messageDto;
    }


    @Mapping(source = "message.message", target = "message")
    @Mapping(source = "message.chat.id", target = "chatId")
    @Mapping(ignore = true, target = "time", expression = "java( new java.time.LocalDateTime())")
    @Mapping(source = "message.userSender.id", target = "senderId")
    @Mapping(source = "message.userSender.imageLink", target = "senderImage")
    @Mapping(source = "message.userSender.nickname", target = "senderNickname")
    public abstract MessageCreateDtoRequest MessageToMessageDto(Message message);


}

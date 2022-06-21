package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.models.dto.MessageCreateDtoResponse;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.ChatRoomService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.controllers.exceptions.ChatNotFoundException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.UserNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class MessagesForGroupChatConverter {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRoomService chatRoomService;


    @Mapping(source = "messageDto.chatId", target = "chat", qualifiedByName = "idToChat")
    @Mapping(source = "messageDto.senderId", target = "userSender", qualifiedByName = "idToUser")
    public abstract Message MessageDtoToMessage(MessageCreateDtoRequest messageDto);

    @Named("idToUser")
    User getUserById(Long id) {
        return userService.getById(id).orElseThrow(() -> new UserNotFoundException("не найден  Юзер с id: " + id));

    }

    @Named("idToChat")
    Chat getChatById(Long id) {
        return chatRoomService.getById(id).orElseThrow(() -> new ChatNotFoundException("не найден  Чат с id: " + id));
    }


    @Mapping(source = "message.chat.id", target = "chatId")
    @Mapping(source = "message.userSender.id", target = "senderId")
    @Mapping(source = "message.userSender.imageLink", target = "senderImage")
    @Mapping(source = "message.userSender.nickname", target = "senderNickname")
    public abstract MessageCreateDtoResponse MessageToMessageDto(Message message);


}

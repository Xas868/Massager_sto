package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoResponse;
import com.javamentor.qa.platform.groupchat.websockets.chatroom.ChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
abstract public class MessagesConverterForChat  {
    ChatRoomService chatRoomService;



//
//    @Mapping(source = "messageRequestDto.message", target = "message")
//    @Mapping(constant = "messageRequest.chatId",qualifiedByName = "chatIdToChat", target = "chat")
//    @Mapping(source = "messageRequestDto.userSender",target = "userSender")
//    public abstract Message changeDtoRequestToMessage(MessageCreateDtoRequest messageRequestDto);
//
//
//
//
//
//    @Named("chatIdToChat")
//    public Chat chatIdToChat(Long chatId) {
//        Optional<Chat> chat = chatRoomService.getById(chatId);
//        if (chat.isPresent()) {
//            return chat.get();
//        }
//        throw new RuntimeException("Некорректный chatId");
//    }
//
//



//    @Mapping(source = "message.id", target = "message_id")
//    @Mapping(source = "message.userSender.nickname", target = "nickname")
//    @Mapping(source = "message.userSender.imageLink", target = "imageLink")
//    @Mapping(constant = "1L", target = "chat_id")
//    @Mapping(source = "message.userSender.id",target = "userSender_id")
//    public abstract MessageCreateDtoResponse changeMessageToDtoResponse(Message message);
}

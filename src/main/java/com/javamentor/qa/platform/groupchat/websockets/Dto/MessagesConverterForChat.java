package com.javamentor.qa.platform.groupchat.websockets.Dto;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.webapp.converters.UserToUserDtoConverter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
abstract public class MessagesConverterForChat  {
    public static MessagesConverterForChat INSTANCE = Mappers.getMapper( MessagesConverterForChat.class );



    @Mapping(source = "messageRequest.message", target = "message")
    public abstract Message changeDtoRequestToMessage(MessageCreateDtoRequest messageRequest);





    @Mapping(source = "message.id", target = "message_id")
    @Mapping(source = "message.userSender.nickname", target = "nickname")
    @Mapping(source = "message.userSender.imageLink", target = "imageLink")
    @Mapping(constant = "1L", target = "chat_id")
    @Mapping(source = "message.userSender.id",target = "userSender_id")
    public abstract MessageCreateDtoResponse changeMessageToDtoResponse(Message message);
}

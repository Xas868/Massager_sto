package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoResponse;
import com.javamentor.qa.platform.models.entity.chat.Message;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
abstract public class MessagesConverterForChat  {




    @Mapping(source = "messageRequest.message", target = "message")
//    @Mapping(constant = "messageRequest.chat_id", target = "message.chat.1L")
//  @Mapping(source = "messageRequest.chat_id",target ="message.chat.id")
    @Mapping(source = "userSender",target = "userSender")
    public abstract Message changeDtoRequestToMessage(MessageCreateDtoRequest messageRequest);






    @Mapping(source = "message.id", target = "message_id")
    @Mapping(source = "message.userSender.nickname", target = "nickname")
    @Mapping(source = "message.userSender.imageLink", target = "imageLink")
    @Mapping(constant = "1L", target = "chat_id")
    @Mapping(source = "message.userSender.id",target = "userSender_id")
    public abstract MessageCreateDtoResponse changeMessageToDtoResponse(Message message);
}

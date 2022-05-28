package com.javamentor.qa.platform.groupchat.websockets.Dto;

import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
public class MessagesConverterForChatImpl extends MessagesConverterForChat {


    @Override
    public Message changeDtoRequestToMessage(MessageCreateDtoRequest messageRequest) {
        if (messageRequest==null){
            return null;

        }
        Message message = new Message();
        message.setMessage(messageRequest.getMessage());
        return message;
    }

    @Override
    public MessageCreateDtoResponse changeMessageToDtoResponse(Message message) {
        if (message==null){
            return null;
        }

        MessageCreateDtoResponse messageResponse = new MessageCreateDtoResponse();
        messageResponse.setMessage_id(message.getId());
        messageResponse.setMessage(message.getMessage());
        messageResponse.setChat_id(1L);
        messageResponse.setNickname(messageDtoResponseUserNickname(message));
        messageResponse.setImageLink(messageDtoResponseImageLink(message));
        messageResponse.setPersistDate(message.getPersistDate());
        messageResponse.setUserSender_id(messageDtoResponseUserSender_id(message));
        return  messageResponse;
    }

    private String messageDtoResponseUserNickname(Message message) {
        if ( message == null ) {
            return null;
        }
        User user = message.getUserSender();
        if ( user == null ) {
            return null;
        }
        String nickname = user.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }
    private String messageDtoResponseImageLink(Message message) {
        if ( message == null ) {
            return null;
        }
        User user = message.getUserSender();
        if ( user == null ) {
            return null;
        }
        String imageLink = user.getImageLink();
        if ( imageLink == null ) {
            return null;
        }
        return imageLink;
    }
    private Long messageDtoResponseUserSender_id(Message message) {
        if ( message == null ) {
            return null;
        }
        User user = message.getUserSender();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

}

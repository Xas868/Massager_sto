package com.javamentor.qa.platform.groupchat.websockets;

import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoResponse;
import com.javamentor.qa.platform.groupchat.websockets.Dto.MessagesConverterForChat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Controller
public class WebSocketBroadcastController {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    MessagesConverterForChat messagesConverterForChat;


    @GetMapping("/stomp-broadcast")
    public String getWebSocketBroadcast() {
        return "stomp-broadcast";
    }


    @MessageMapping("/broadcast")//@MessageMapping аннотация гарантирует, что если сообщение
    // отправляется на /app/broadcast, то будет вызван send() метод.
    @SendTo("/topic/messages")// Возвращаемое значение рассылается всем подписчикам на /topic/messages,
    // как это определено в аннотации @sendTo.
    @Transactional
    public Message send(@Payload MessageCreateDtoRequest messageRequest) {
        Message message = messagesConverterForChat.changeDtoRequestToMessage(messageRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        message.setUserSender((User) authentication.getPrincipal());
      entityManager.persist(message);
      return message;

//         return  messagesConverter.changeMessageToDtoResponse(message);





    }
}

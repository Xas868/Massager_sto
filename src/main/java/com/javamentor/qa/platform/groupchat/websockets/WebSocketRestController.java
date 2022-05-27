package com.javamentor.qa.platform.groupchat.websockets;

import com.javamentor.qa.platform.models.entity.chat.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/groupchat/")
public class WebSocketRestController {

    @PersistenceContext
    EntityManager entityManager;

    @MessageMapping("/broadcast")//@MessageMapping аннотация гарантирует, что если сообщение
    // отправляется на /app/broadcast, то будет вызван send() метод.
    @SendTo("/topic/messages")// Возвращаемое значение рассылается всем подписчикам на /topic/messages,
    // как это определено в аннотации @sendTo.
    @Transactional
    public void send(@Payload Message chatMessage) {
//        Long id =null;
//
//        if (chatMessage.getChat().getId()==null){
//            id=1L;
//            chatMessage.getChat().setId(id);
//        }

        chatMessage.setPersistDate(LocalDateTime.now());

        entityManager.persist(chatMessage);
        //persist
    }
}

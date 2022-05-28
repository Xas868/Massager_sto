package com.javamentor.qa.platform.groupchat.websockets;

import com.javamentor.qa.platform.groupchat.websockets.chatroom.ChatRoomDao;
import com.javamentor.qa.platform.groupchat.websockets.chatroom.ChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/groupchat/")
public class WebSocketRestController {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ChatRoomService chatRoomService;



//Optional<Long> chatId =chatRoomService.findIdChatByUser_Sender(chatMessage.getUserSender().getId(),true);



//        entityManager.persist(chatMessage);
//        //persist
//    }
}


//    var chatId = chatRoomService
//            .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
//        chatMessage.setChatId(chatId.get());
//        Long id =null;
//
//        if (chatMessage.getChat().getId()==null){
//            id=1L;
//            chatMessage.getChat().setId(id);
//        }
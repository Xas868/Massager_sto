package com.javamentor.qa.platform.groupchat.websockets;

import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.groupchat.websockets.chatmesseges.ChatMessagesService;
import com.javamentor.qa.platform.groupchat.websockets.chatroom.ChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.webapp.converters.MessagesConverterForChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;


@Controller
public class WebSocketBroadcastController {


    @Autowired
    MessagesConverterForChat messagesConverterForChat;

    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatMessagesService chatMessagesService;

    @PersistenceContext
    EntityManager entityManager;


    @GetMapping("/stomp-broadcast")
    public String getWebSocketBroadcast() {
        return "stomp-broadcast";
    }

    Chat chat = (Chat.builder().chatType(ChatType.GROUP).title("Group")
            .persistDate(LocalDateTime.now()).id(1L).build());

    @MessageMapping("/broadcast")//@MessageMapping аннотация гарантирует, что если сообщение
    // отправляется на /app/broadcast, то будет вызван send() метод.
    @SendTo("/topic/messages")// Возвращаемое значение рассылается всем подписчикам на /topic/messages,
    // как это определено в аннотации @sendTo.


    @Transactional
    public void send(@Payload MessageCreateDtoRequest messageRequest) {


//        Message message = messagesConverterForChat.changeDtoRequestToMessage(messageRequest);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        message.setUserSender((User) authentication.getPrincipal());


        Long id = chat.getId();

        if (chat.getId() >= id) {
            ++id;
            chat.setId(id);

        }


        Message message = new Message();


        entityManager.persist(chat);

        message.setMessage(messageRequest.getMessage());
        message.setChat(chat);
        message.setUserSender(User.builder().id(100L).email("user100@mail.ru").isDeleted(false).isEnabled(true).password("user100")
                .role(Role.builder().id(1L).build()).build());
//
//        message.setUserSender(User.builder().id(101L).email("1").password("1")
//                .role(Role.builder().id(1L).build()).build());
//      message.setUserSender(user);

        chatMessagesService.persist(message);


//        return message;


    }


}

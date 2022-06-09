package com.javamentor.qa.platform.groupchat.websockets;

import com.javamentor.qa.platform.groupchat.websockets.Dto.MessageCreateDtoRequest;
import com.javamentor.qa.platform.groupchat.websockets.chatmesseges.ChatMessagesService;
import com.javamentor.qa.platform.groupchat.websockets.chatroom.ChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.Message;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.MessagesConverterForChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;


@Controller
public class WebSocketBroadcastController {


    @Autowired
    MessagesConverterForChat messagesConverterForChat;

    @Autowired
    ChatRoomService chatRoomService;
    @Autowired
    ChatMessagesService chatMessagesService;
    @Autowired
    UserService userService;




    @GetMapping("/stomp-broadcast")
    public String getWebSocketBroadcast() {
        return "stomp-broadcast";
    }


//    User user = (User.builder().id(100L).email("user100@mail.ru").nickname("user_100").isDeleted(false).imageLink("/images/noUserAvatar.png").isEnabled(true).password("user100")
//            .role(Role.builder().id(1L).build()).build());

    @MessageMapping("/broadcast")//@MessageMapping аннотация гарантирует, что если сообщение
    // отправляется на /app/broadcast, то будет вызван send() метод.
    @SendTo("/topic/messages")// Возвращаемое значение рассылается всем подписчикам на /topic/messages,
    // как это определено в аннотации @sendTo.


    @Transactional
    public MessageCreateDtoRequest send(@Payload MessageCreateDtoRequest messageRequestDto) {
//
        Message message = new Message();
        Chat chat = new Chat();
        chat.setChatType(ChatType.GROUP);
        chat.setTitle("gr");
        chat.setId(1L);
       chatRoomService.update(chat);

        message.setMessage(messageRequestDto.getMessage());
        message.setChat(chat);
        message.setUserSender(userById(messageRequestDto.getSenderId()));
        message.setPersistDate(LocalDateTime.now());
        chatMessagesService.persist(message);

        return new MessageCreateDtoRequest(messageRequestDto.getMessage(),
               messageRequestDto.getSenderId(),
                messageRequestDto.getTime(),
                messageRequestDto.getChatId(),
                messageRequestDto.getSenderNickname(),
                messageRequestDto.getSenderImage());


    }

   User userById(Long id){
        return userService.getById(id).orElse(null);
    }


}

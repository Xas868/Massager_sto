package com.javamentor.qa.platform.groupchat.websockets.chatroom;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.service.impl.model.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl extends ReadWriteServiceImpl<Chat, Long> implements ChatRoomService {

    @Autowired
    ChatRoomDao chatRoomDao;

    public ChatRoomServiceImpl(ReadWriteDao<Chat, Long> readWriteDao) {
        super(readWriteDao);


    }

    public Optional<Long> findIdChatByUser_Sender(Long userSender, boolean createIfNotExist) {// здесь нахождение
        // id чата по id отправителя. Но надо ли ? Если  у нас только 1 групповой чат .
        return chatRoomDao.findIdChatByUser_Sender(userSender).map(Chat::getId)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }

                    Long chatId = userSender;


                    Chat singleGroupChat =Chat.builder()
                            .id(chatId)
                            .chatType(ChatType.GROUP)
                            .persistDate(LocalDateTime.now())
                            .title("GroupChat")
                            .build();
                    chatRoomDao.persist(singleGroupChat);
                    return Optional.of(userSender);

                });

    }
}


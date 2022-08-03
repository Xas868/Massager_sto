package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ChatRoomDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.service.abstracts.model.ChatRoomService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ToString
public class ChatRoomServiceImpl extends ReadWriteServiceImpl<Chat, Long> implements ChatRoomService {

    @Autowired
   private ChatRoomDao chatRoomDao;


    public ChatRoomServiceImpl(ReadWriteDao<Chat, Long> readWriteDao, ChatRoomDao chatRoomDao) {
        super(readWriteDao);
        this.chatRoomDao = chatRoomDao;
    }
}



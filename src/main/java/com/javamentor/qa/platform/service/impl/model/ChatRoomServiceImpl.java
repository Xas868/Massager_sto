package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ChatRoomDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.service.abstracts.model.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImpl extends ReadWriteServiceImpl<Chat, Long> implements ChatRoomService {

    public ChatRoomServiceImpl(ChatRoomDao chatRoomDao) {
        super(chatRoomDao);
    }
}



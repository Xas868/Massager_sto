package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ChatRoomDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.dao.impl.model.GroupChatRoomDaoImpl;
import com.javamentor.qa.platform.dao.impl.model.SingleChatDaoImpl;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.service.abstracts.model.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatRoomServiceImpl extends ReadWriteServiceImpl<Chat, Long> implements ChatRoomService {
    @Autowired
    private ChatRoomDao chatRoomDao;

    @Autowired
    private GroupChatRoomDaoImpl groupChatRoomDao;

    @Autowired
    private SingleChatDaoImpl singleChatDao;


    public ChatRoomServiceImpl(ReadWriteDao<Chat, Long> readWriteDao, ChatRoomDao chatRoomDao) {
        super(readWriteDao);
        this.chatRoomDao = chatRoomDao;
    }

    @Transactional
    public void deleteUserFromChatById(Long chatId, Long userId) {

        ChatType chatType = chatRoomDao.getById(chatId).get().getChatType();
        if (chatType.equals(ChatType.GROUP)) {
            groupChatRoomDao.deleteById(chatId,userId);
        } else {
            singleChatDao.deleteById(chatId, userId);
        }
    }
}



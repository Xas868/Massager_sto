package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.model.ReadWriteDao;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;

import org.springframework.stereotype.Service;

@Service
public class GroupChatRoomServiceImpl extends ReadWriteServiceImpl<GroupChat,Long> implements GroupChatRoomService {
    public GroupChatRoomServiceImpl(ReadWriteDao<GroupChat, Long> readWriteDao) {
        super(readWriteDao);
    }
}

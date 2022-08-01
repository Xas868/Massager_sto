package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;
import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupChatRoomServiceImpl extends ReadWriteServiceImpl<GroupChat, Long> implements GroupChatRoomService {

    private final GroupChatRoomDao groupChatRoomDao;

    public GroupChatRoomServiceImpl(ReadWriteDao<GroupChat, Long> readWriteDao, GroupChatRoomDao groupChatRoomDao) {
        super(readWriteDao);
        this.groupChatRoomDao = groupChatRoomDao;
    }


    @Override
    public Optional<GroupChat> getGroupChatAndUsers(long id) {
        return groupChatRoomDao.getGroupChatAndUsers(id);
    }
}


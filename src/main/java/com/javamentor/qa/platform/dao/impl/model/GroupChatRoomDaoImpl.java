package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;

import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Service;

@Service
public class GroupChatRoomDaoImpl extends ReadWriteDaoImpl<GroupChat, Long> implements GroupChatRoomDao {
}

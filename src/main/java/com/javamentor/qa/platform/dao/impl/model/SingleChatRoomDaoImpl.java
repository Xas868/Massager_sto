package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.SingleChatRoomDao;
import com.javamentor.qa.platform.models.entity.chat.SingleChat;
import org.springframework.stereotype.Service;

@Service
public class SingleChatRoomDaoImpl extends ReadWriteDaoImpl<SingleChat, Long> implements SingleChatRoomDao {
}

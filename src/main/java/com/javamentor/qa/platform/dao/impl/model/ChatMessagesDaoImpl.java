package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.ChatMessagesDao;

import com.javamentor.qa.platform.models.entity.chat.Message;
import org.springframework.stereotype.Repository;

@Repository
public class ChatMessagesDaoImpl extends ReadWriteDaoImpl<Message, Long> implements ChatMessagesDao {
}

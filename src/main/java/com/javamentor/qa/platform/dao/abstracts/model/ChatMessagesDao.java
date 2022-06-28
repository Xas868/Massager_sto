package com.javamentor.qa.platform.dao.abstracts.model;


import com.javamentor.qa.platform.dao.impl.model.model.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.chat.Message;

public interface ChatMessagesDao extends ReadWriteDao<Message, Long> {


}

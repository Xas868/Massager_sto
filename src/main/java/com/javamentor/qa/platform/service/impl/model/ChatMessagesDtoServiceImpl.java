package com.javamentor.qa.platform.service.impl.model;


import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.ChatMessageDto;

import com.javamentor.qa.platform.service.abstracts.model.ReadWriteService;

public class ChatMessagesDtoServiceImpl extends ReadWriteServiceImpl<ChatMessageDto,Long>  implements ReadWriteService<ChatMessageDto, Long> {


    public ChatMessagesDtoServiceImpl(ReadWriteDao<ChatMessageDto, Long> readWriteDao) {
        super(readWriteDao);
    }






}

package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.UserChatPinDao;
import com.javamentor.qa.platform.models.entity.user.UserChatPin;
import com.javamentor.qa.platform.service.abstracts.model.UserChatPinService;
import org.springframework.stereotype.Service;

@Service
public class UserChatPinServiceImpl extends ReadWriteServiceImpl<UserChatPin, Long> implements UserChatPinService {
    public UserChatPinServiceImpl(UserChatPinDao userChatPinDao) {
        super(userChatPinDao);
    }
}

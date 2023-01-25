package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.UserBlock;

import java.util.Optional;


    public interface UserBlockedDao extends ReadWriteDao<UserBlock, Long> {

        void deleteUserFromBlockById(Long profile, Long block);


    }



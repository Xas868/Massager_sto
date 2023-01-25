package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.UserBlock;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserBlockedService extends ReadWriteService<UserBlock, Long> {

    @Transactional
    void deleteUserFromBlockById(Long profile, Long block);

}
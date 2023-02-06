package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;
import org.springframework.transaction.annotation.Transactional;

public interface BlockChatUserListService extends ReadWriteService<BlockChatUserList, Long> {
    @Transactional
    void deleteUserFromBlockById(Long profile, Long blocked);
    Boolean isExistsUserFromBlockById(Long profilId, Long blockId);
}

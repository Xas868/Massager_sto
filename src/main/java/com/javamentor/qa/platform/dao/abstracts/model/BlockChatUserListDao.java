package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;

public interface BlockChatUserListDao extends ReadWriteDao<BlockChatUserList, Long>{


    void deleteUserFromBlockById(Long profile, Long blocked);
}

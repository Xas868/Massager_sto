package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BlockChatUserListDao;
import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;
import com.javamentor.qa.platform.service.abstracts.model.BlockChatUserListService;
import org.springframework.stereotype.Service;


@Service
public class BlockChatUserListServiceImpl extends ReadWriteServiceImpl<BlockChatUserList, Long> implements BlockChatUserListService {

    private final BlockChatUserListDao blockChatUserListDao;


    void deleteUserFromBlockById(Long profile, Long blocked){
        blockChatUserListDao.deleteUserFromBlockById(profile,blocked);
    }
    public BlockChatUserListServiceImpl(BlockChatUserListDao blockChatUserListDao) {
        super(blockChatUserListDao);
        this.blockChatUserListDao = blockChatUserListDao;
    }
}

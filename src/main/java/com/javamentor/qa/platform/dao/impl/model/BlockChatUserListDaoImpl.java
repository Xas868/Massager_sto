package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.BlockChatUserListDao;
import com.javamentor.qa.platform.models.entity.chat.BlockChatUserList;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BlockChatUserListDaoImpl extends ReadWriteDaoImpl<BlockChatUserList, Long> implements BlockChatUserListDao {

    @PersistenceContext
    EntityManager entityManager;
}

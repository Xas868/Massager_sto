package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ChatDtoDaoImpl implements ChatDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GroupChatDto> getGroupChatDtoById(long chatId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.GroupChatDto" +
                "(" +
                    "gc.id, " +
                    "gc.chat.title, " +
                    "gc.chat.persistDate" +
                ") from GroupChat as gc where gc.id = :chatId", GroupChatDto.class )
                .setParameter("chatId", chatId));
    }
}

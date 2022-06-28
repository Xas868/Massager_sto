package com.javamentor.qa.platform.dao.impl.pagination.messagedto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository ("MessagePageDtoBySingleChatId")
public class MessagePageDtoBySingleChatId implements PageDtoDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getPaginationItems(PaginationData properties) {
        long singleChatID = (long) properties.getProps().get("singleChatId");
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.MessageDto " +
                "(message.id, " +
                "message.message, " +
                "message.userSender.nickname, " +
                "message.userSender.id, " +
                "message.userSender.imageLink, " +
                "message.persistDate) " +
                "from Message as message inner join SingleChat as single_chat on message.chat.id = single_chat.chat.id " +
                "where single_chat.id = :singleChatId order by message.persistDate desc", MessageDto.class)
                .setParameter("singleChatId", singleChatID)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        long singleChatId = (long) properties.get("singleChatId");
        return (long) entityManager.createQuery("select count (message.id) " +
                "from Message as message inner join SingleChat as single_chat on message.chat.id = single_chat.chat.id " +
                "where single_chat.id = :singleChatId")
                .setParameter("singleChatId", singleChatId)
                .getSingleResult();
    }
}

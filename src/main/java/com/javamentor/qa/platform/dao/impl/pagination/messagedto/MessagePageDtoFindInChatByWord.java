package com.javamentor.qa.platform.dao.impl.pagination.messagedto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository ("MessagePageDtoFindInChatByWord")
public class MessagePageDtoFindInChatByWord implements PageDtoDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        long chatID = (long) properties.getProps().get("chatId");
        String searchWord = (String) properties.getProps().get("searchWord");

        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.MessageDto " +
                        "(message.id, " +
                        "message.message, " +
                        "message.userSender.nickname, " +
                        "message.userSender.id, " +
                        "message.userSender.imageLink, " +
                        "message.persistDate) " +
                        "from Message as message inner join Chat as chat on message.chat.id = chat.id " +
                        "where chat.id = :chatId and message.message like :searchWord order by message.persistDate desc", MessageDto.class)
                .setParameter("chatId", chatID)
                .setParameter("searchWord", "%"+searchWord+"%")
                .setFirstResult((properties.getCurrentPage() - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        long chatId = (long) properties.get("chatId");
        String searchWord = (String) properties.get("searchWord");
        return (long) entityManager.createQuery("select count (message.id) " +
                        "from Message as message inner join Chat as chat on message.chat.id = chat.id " +
                        "where chat.id = :chatId and message.message like :searchWord")
                .setParameter("chatId", chatId)
                .setParameter("searchWord", "%"+searchWord+"%")
                .getSingleResult();
    }
}

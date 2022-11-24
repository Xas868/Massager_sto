package com.javamentor.qa.platform.dao.impl.pagination.messagedto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("MessagePageDtoFindInChatByWord")
public class MessagePageDtoFindInChatByWord implements PageDtoDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getPaginationItems(PaginationData properties) {
        long items = properties.getItemsOnPage();
        long chatId = (long) properties.getProps().get("id");
        String word = (String) properties.getProps().get("word");
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.MessageDto " +
                        "(message.id, " +
                        "message.message, " +
                        "message.userSender.nickname, " +
                        "message.userSender.id, " +
                        "message.userSender.imageLink, " +
                        "message.persistDate) " +
                        "from Message as message inner join Chat as chat on message.chat.id = chat.id " +
                        "where chat.id = :id and message.message like :word order by message.persistDate desc", MessageDto.class)
                .setParameter("id", chatId)
                .setParameter("word", "%" + word + "%")
                .setFirstResult((int) ((properties.getCurrentPage() - 1) * items))
                .setMaxResults((int) items)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        long chatId = (long) properties.get("id");
        String word = (String) properties.get("word");
        return (long) entityManager.createQuery("select count (message.id) " +
                        "from Message as message inner join Chat as chat on message.chat.id = chat.id " +
                        "where chat.id = :chatId and message.message like :word")
                .setParameter("chatId", chatId)
                .setParameter("word", "%" + word + "%").getSingleResult();
    }
}

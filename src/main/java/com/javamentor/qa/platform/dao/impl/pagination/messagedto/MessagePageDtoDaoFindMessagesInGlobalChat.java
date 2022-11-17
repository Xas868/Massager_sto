package com.javamentor.qa.platform.dao.impl.pagination.messagedto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("MessagePageDtoDaoFindMessagesInGlobalChat")
public class MessagePageDtoDaoFindMessagesInGlobalChat implements PageDtoDao<MessageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int firstResultOffset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.MessageDto " +
                        "(m.id, " +
                        "m.message, " +
                        "m.userSender.fullName, " +
                        "m.userSender.id, " +
                        "m.userSender.imageLink, " +
                        "m.persistDate) from Message as m inner join Chat as c on c.id = m.chat.id " +
                        "where c.isGlobal = true and m.message like concat('%',:text,'%') " +
                        "order by m.persistDate desc", MessageDto.class)
                .setParameter("text",properties.getProps().get("text"))
                .setFirstResult(firstResultOffset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("select count (m.id) " +
                        "from Message as m inner join Chat as c on m.chat.id = c.id " +
                        "where c.isGlobal = true and m.message like concat('%',:text,'%')")
                .setParameter("text",properties.get("text"))
                .getSingleResult();
    }
}
package com.javamentor.qa.platform.dao.impl.pagination.messagedto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.MessageDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("MessagePageDtoDaoAllMessagesInGlobalChat")
public class MessagePageDtoDaoAllMessagesInGlobalChat implements PageDtoDao<MessageDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MessageDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int firstResultOffset = (properties.getCurrentPage() - 1) * itemsOnPage;
        return entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.MessageDto " +
                        "(m.id, " +
                        "m.message, " +
                        "m.userSender.nickname, " +
                        "m.userSender.id, " +
                        "m.userSender.imageLink, " +
                        "m.persistDate) from Message as m inner join Chat as c on c.id = m.chat.id " +
                        "where c.isGlobal = true " +
                        "order by m.persistDate desc", MessageDto.class)
                .setFirstResult(firstResultOffset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (long) (entityManager.createQuery("select count (m.id) " +
                        "from Message as m inner join Chat as c on m.chat.id = c.id " +
                        "where c.isGlobal = true ")
                .getSingleResult());
    }
}

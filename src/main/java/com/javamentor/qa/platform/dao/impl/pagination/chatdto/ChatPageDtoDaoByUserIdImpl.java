package com.javamentor.qa.platform.dao.impl.pagination.chatdto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.dto.ChatDto;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.AnswerDTO;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository("ChatPageDtoDaoByUserIdImpl")
public class ChatPageDtoDaoByUserIdImpl implements PageDtoDao<ChatDto>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChatDto> getPaginationItems(PaginationData properties) {
        int itemsOnPage = properties.getItemsOnPage();
        int offset = (properties.getCurrentPage() - 1) * itemsOnPage;
        Long userId = (long) properties.getProps().get("userId");
        return entityManager
                .createQuery("SELECT new com.javamentor.qa.platform.models.dto.ChatDto(   " +
                        // Chat ID
                                "       chat.id, " +
                                // Now we return the group name OR user nickname depending on chat type
                                "       case when chat.chatType = :group " +
                                "               then groupChat.title " +
                                "       else " +
                                "           case when user1.id = :userId " +
                                "               then user2.nickname " +
                                "           else " +
                                "               user1.nickname " +
                                "           end " +
                                "       end, " +
                                // Then we want to return image link for chat, but if it's a single chat then we have to return profile picture of a person who our person is chatting with.
                                "       case when chat.chatType = :group " +
                                "               then chat.image " +
                                "       else case when user1.id = :userId " +
                                "               then user2.imageLink " +
                                "               else case when user2.id = :userId " +
                                "                   then user1.imageLink " +
                                "                   else '<no-image-link-found>' " +
                                "                   end " +
                                "               end " +
                                "       end, " +
                                // Now we need to return last message that was sent in the chat
                                "       case when (select count(message.id) from Message as message where message.chat.id = chat.id) > 0 then" +
                                "           (select coalesce(message.message, '<no-messages-found>') " +
                                "                   from Message as message " +
                                "                   where message.chat.id = chat.id " +
                                "                   and message.persistDate = ( " +
                                "                       select max (message.persistDate) " +
                                "                             from Message as message " +
                                "                            where chat.id = message.chat.id))" +
                                "       else '<no-messages-found>' end," +
                                // Same with persist date of the message
                                "       case when (select count(message.id) from Message as message where message.chat.id = chat.id) > 0 then " +
                                "   (select coalesce(message.persistDate, '0001-01-01 00:00:00') " +
                                "               from Message as message " +
                                "              where message.chat.id = chat.id" +
                                "                and message.persistDate = ( " +
                                "                       select max (message.persistDate) " +
                                "                             from Message as message " +
                                "                            where chat.id = message.chat.id)) " +
                                "       else coalesce(chat.persistDate, '0001-01-01 00:00:00')  end," +
                                "((select count(ucp.id) from UserChatPin ucp where ucp.chat.id = chat.id and ucp.user.id = :userId) > 0)) " +
                                "     from Chat as chat " +
                                "left join GroupChat as groupChat " +
                                "       on chat.id = groupChat.id " +
                                "left join SingleChat as singleChat " +
                                "       on chat.id = singleChat.id " +
                                "left join User as user1 " +
                                "       on singleChat.userOne.id = user1.id " +
                                "left join User as user2 " +
                                "       on singleChat.useTwo.id = user2.id " +
                                // Now we are filtering out all the chats that we don't need
                                "where :userId in elements(groupChat.users) or user1.id = :userId or user2.id = :userId",
                        ChatDto.class)
                .setParameter("userId", userId)
                .setParameter("group", ChatType.GROUP)
                .setFirstResult(offset)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getTotalResultCount(Map<String, Object> properties) {
        return (Long) entityManager.createQuery("select count(chat.id) from Chat chat " +
                                "left join GroupChat as groupChat " +
                                "on chat.id = groupChat.id " +
                                "left join SingleChat as singleChat " +
                                "       on chat.id = singleChat.id " +
                                "left join User as user1 " +
                                "       on singleChat.userOne.id = user1.id " +
                                "left join User as user2 " +
                                "       on singleChat.useTwo.id = user2.id " +
                                "where user1.id = :userId or user2.id = :userId or :userId in elements(groupChat.users)")
                .setParameter("userId", properties.get("userId"))
                .getSingleResult();
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}

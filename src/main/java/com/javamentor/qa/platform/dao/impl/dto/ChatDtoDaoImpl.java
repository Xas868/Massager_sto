package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.ChatDto;
import com.javamentor.qa.platform.models.dto.ChatUserDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ChatDtoDaoImpl implements ChatDtoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SingleChatDto> getAllSingleChatDtoByUserId(Long userId) {
        return entityManager.createQuery(
                        "SELECT new com.javamentor.qa.platform.models.dto.SingleChatDto (" +
                                "sc.id, " +
                                "(CASE WHEN sc.userOne.id = :userId THEN sc.useTwo.nickname ELSE sc.userOne.nickname END), " +
                                "(CASE WHEN sc.userOne.id = :userId THEN sc.useTwo.imageLink ELSE sc.userOne.imageLink END), " +
                                "(SELECT me.message FROM Message AS me WHERE sc.chat.id = me.chat.id AND " +
                                "me.persistDate = (SELECT max(mes.persistDate) FROM Message mes WHERE sc.chat.id = mes.chat.id)), " +
                                " (SELECT me.persistDate FROM Message AS me WHERE sc.chat.id = me.chat.id AND " +
                                "me.persistDate = (SELECT max(mes.persistDate) FROM Message mes WHERE sc.chat.id = mes.chat.id))) " +
                                "FROM SingleChat AS sc JOIN User AS ue " +
                                "ON ue.id = sc.userOne.id OR ue.id = sc.useTwo.id " +
                                "WHERE ue.id = :userId ", SingleChatDto.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<ChatUserDto> getChatUsersDtoByChatId(Long chatId) {
        return entityManager.createQuery("select distinct new com.javamentor.qa.platform.models.dto.ChatUserDto" +
                                "(" +
                                "u.id, " +
                                "u.fullName," +
                                "u.imageLink, " +
                                "CASE WHEN  m.id = u.id THEN 'MODERATOR' else (case when u.id = gc.userAuthor.id then 'AUTHOR' end) end" +
                                ") " +
                                "from GroupChat as gc join gc.users u join gc.moderators m where gc.id = :chatId  "
                        , ChatUserDto.class)
                .setParameter("chatId", chatId)
                .getResultList();
    }

    @Override
    public Optional<GroupChatDto> getGroupChatDto(long chatId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.GroupChatDto" +
                        "(" +
                        "gc.id, " +
                        "gc.title," +
                        "gc.image, " +
                        "gc.chat.persistDate" +
                        ") " +
                        "from GroupChat as gc where gc.id = :chatId " +
                        "order by gc.chat.persistDate", GroupChatDto.class)
                .setParameter("chatId", chatId));
    }

    public Boolean isExistUserOfGroupChat(long id, long chatId) {
        if (entityManager.createQuery
                        (" select u from  GroupChat as gc join gc.users u where u.id =: id AND gc.id =: chatId  ", User.class)
                .setParameter("id", id)
                .setParameter("chatId", chatId).getResultList().isEmpty()) {return false;} else {return true;}
    }
}

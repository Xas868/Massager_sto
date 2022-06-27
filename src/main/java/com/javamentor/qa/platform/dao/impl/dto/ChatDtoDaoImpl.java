package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
}

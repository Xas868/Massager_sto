package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.MessageStarDao;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.MessageStar;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class MessageStarDaoImpl extends ReadWriteDaoImpl<MessageStar, Long> implements MessageStarDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<MessageStar> getMessageByUserAndMessage(Long userId, Long messageId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager
                .createQuery("SELECT m FROM MessageStar m JOIN FETCH m.user " +
                        "JOIN FETCH m.message WHERE m.user.id =:userId AND m.message.id =:messageId", MessageStar.class)
                .setParameter("userId", userId)
                .setParameter("messageId", messageId));
    }
}

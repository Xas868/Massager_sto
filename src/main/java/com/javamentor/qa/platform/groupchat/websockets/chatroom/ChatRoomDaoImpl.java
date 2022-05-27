package com.javamentor.qa.platform.groupchat.websockets.chatroom;

import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.entity.chat.Chat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
@Repository
public class ChatRoomDaoImpl extends ReadWriteDaoImpl<Chat, Long>  implements ChatRoomDao {

    @PersistenceContext
    EntityManager entityManager;



   public Optional<Chat> findIdChatByUser_Sender(Long userSenderId){
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("" +
                "select  c from Chat c where Message.userSender.id=:userSenderId",Chat.class)
                .setParameter("userSenderId",userSenderId));



    }



}

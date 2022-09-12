package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.ChatDtoDao;
import com.javamentor.qa.platform.models.dto.ChatDto;
import com.javamentor.qa.platform.models.dto.SingleChatDto;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.GroupChatDto;
import com.javamentor.qa.platform.models.entity.chat.ChatType;
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
    public Optional<GroupChatDto> getGroupChatDto(long chatId) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select new com.javamentor.qa.platform.models.dto.GroupChatDto" +
                        "(" +
                        "gc.id, " +
                        "gc.title," +
                        "gc.chat.image, " +
                        "gc.chat.persistDate" +
                        ") " +
                        "from GroupChat as gc where gc.id = :chatId " +
                        "order by gc.chat.persistDate", GroupChatDto.class)
                .setParameter("chatId", chatId));
    }

    @Override
    public List<ChatDto> getAllChatsByNameAndUserId(String chatName, Long userId) {
        return entityManager.createQuery(
                        // Building ChatDto
                        "SELECT NEW com.javamentor.qa.platform.models.dto.ChatDto( " +
                                // First we return chat id no matter what type this chat is
                                "       chat.id, " +

                                "       case when chat.chatType = :group " +
                                "           and lower(groupChat.title) like :chatName" +
                                "               then groupChat.title " +
                                "       else case when user1.id = :userId " +
                                "          then case when lower(user2.nickname) like :chatName " +
                                "              then user2.nickname " +
                                "              else '<no-chat-found>' " +
                                "              end " +
                                "          else case when user2.id = :userId " +
                                "              then case when lower(user1.nickname) like :chatName " +
                                "                  then user1.nickname " +
                                "                  else '<no-chat-found>' " +
                                "                  end " +
                                "              else '<no-chat-found>' " +
                                "              end " +
                                "          end " +
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
                                "       (select coalesce(message.message, '<no-messages-found>') " +
                                "               from Message as message " +
                                "              where message.chat.id = chat.id " +
                                "                and message.persistDate = ( " +
                                "                       select max (message.persistDate) " +
                                "                             from Message as message " +
                                "                            where chat.id = message.chat.id))," +
                                // Same with persist date of the message
                                "       (select coalesce(message.persistDate, '0001-01-01 00:00:00') " +
                                "               from Message as message " +
                                "              where message.chat.id = chat.id" +
                                "                and message.persistDate = ( " +
                                "                       select max (message.persistDate) " +
                                "                             from Message as message " +
                                "                            where chat.id = message.chat.id)))" +
                                "     from Chat as chat " +
                                "left join GroupChat as groupChat " +
                                "       on chat.id = groupChat.id " +
                                "left join SingleChat as singleChat " +
                                "       on chat.id = singleChat.id " +
                                "left join User as user1 " +
                                "       on singleChat.userOne.id = user1.id " +
                                "left join User as user2 " +
                                "       on singleChat.useTwo.id = user2.id ",
                        ChatDto.class)
                .setParameter("chatName", '%' + chatName + '%')
                .setParameter("group", ChatType.GROUP)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<ChatDto> getAllChatsByUserId(Long userId) {
        System.out.println("userId = " + userId);
        System.out.println("");
        System.out.println("popitka poluchit List");
//        ChatDto chatDto1 = new ChatDto();
//        chatDto1.setId(105L);
//        chatDto1.setImage("image for chat id 101");
//      List<ChatDto> list = new ArrayList<>();
//      list.add(chatDto1);
        String snull = "";
        List<ChatDto> list1 = entityManager.createQuery(
                        // Building ChatDto
                        "SELECT NEW com.javamentor.qa.platform.models.dto.ChatDto( " +
                                // First we return chat id no matter what type this chat is
                                "       chat.id, " +
                                "       case when chat.chatType = :single " +
                                "           then case when user1.id = :userId " +
                                "               then user2.nickname" +
                                "               else  case when user2.id = :userId" +
                                "                   then user1.nickname " +
                                "                   else '<no-chat-found>'" +
                                "                    end" +
                                "               end" +
//                                "               end" +

                                "           else (select groupChat.title from groupChat where groupChat.id = (select chat_id from Users u join u.userId where user_id = :userId))" +

//                                "groupChat.id = (select chat_id from Groupchat_has_users as groupchat_has_users where user_id = :userId )) " +
//                                ":userId in (groupChat.users) " +
                                "           end" +
//                                "                   else '<no-chat-found>' " +
//                                "                   end " +



//                                "       case when chat.chatType = :group " +
//                                "           then " +
//                                "               case when groupChat.title <> :snull  " +
////                                ":userId in (groupChat.users) " +
//                                "                   then (select groupChat.title from groupChat where :userId in (groupChat.users)) " +
//                                "                   else '<no-chat-found>' " +
//                                "                   end " +
//                                "       else case when user1.id = :userId " +
//                                "               then user2.nickname" +
//                                "               else  case when user2.id = :userId" +
//                                "                   then user1.nickname " +
//                                "                   else '<no-chat-found>'" +
//                                "                    end" +
//                                "               end" +
//                                "           end" +
                                "," +
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
                                "       (select coalesce(message.message, '<no-messages-found>') " +
                                "               from Message as message " +
                                "              where message.chat.id = chat.id " +
                                "                and message.persistDate = ( " +
                                "                       select max (message.persistDate) " +
                                "                             from Message as message " +
                                "                            where chat.id = message.chat.id))," +
                                // Same with persist date of the message
                                "       (select coalesce(message.persistDate, '0001-01-01 00:00:00') " +
                                "               from Message as message " +
                                "              where message.chat.id = chat.id" +
                                "                and message.persistDate = ( " +
                                "                       select max (message.persistDate) " +
                                "                             from Message as message " +
                                "                            where chat.id = message.chat.id)))" +
                                "     from Chat as chat " +
                                "left join GroupChat as groupChat " +
                                "       on chat.id = groupChat.id " +
                                "left join SingleChat as singleChat " +
                                "       on chat.id = singleChat.id " +
                                "left join User as user1 " +
                                "       on singleChat.userOne.id = user1.id " +
                                "left join User as user2 " +
                                "       on singleChat.useTwo.id = user2.id ",
                        ChatDto.class)

                .setParameter("single", ChatType.SINGLE)
                .setParameter("group", ChatType.GROUP)
                .setParameter("userId", userId)
//                .setParameter("snull", snull)
                .getResultList();

        System.out.println("Udalos");

        return list1;
    }
    @Override
    public List<ChatDto> getAllChatsByChatNameOrUserId(String nameChat, Long userId) {
//        List<ChatDto> list1 = entityManager.createQuery(
//                        // Building ChatDto
//                        "SELECT NEW com.javamentor.qa.platform.models.dto.ChatDto( " +
//                                // First we return chat id no matter what type this chat is
//                                "       chat.id, " +
//                                "       case when chat.chatType = :group " +
//                                "           then " +
//                                "               case when (select groupChat.title from groupChat where :userId in (groupChat.users)) " +
////                                ":userId in (groupChat.users) " +
//                                "                   then groupChat.title " +
//                                "                   else '<no-chat-found>' " +
//                                "                   end " +
//                                "       else case when user1.id = :userId " +
//                                "               then user2.nickname" +
//                                "               else  case when user2.id = :userId" +
//                                "                   then user1.nickname " +
//                                "                   else '<no-chat-found>'" +
//                                "                    end" +
//                                "               end" +
//                                "           end" +
//                                "," +
//                                // Then we want to return image link for chat, but if it's a single chat then we have to return profile picture of a person who our person is chatting with.
//                                "       case when chat.chatType = :group " +
//                                "               then chat.image " +
//                                "       else case when user1.id = :userId " +
//                                "               then user2.imageLink " +
//                                "               else case when user2.id = :userId " +
//                                "                   then user1.imageLink " +
//                                "                   else '<no-image-link-found>' " +
//                                "                   end " +
//                                "               end " +
//                                "       end, " +
//                                // Now we need to return last message that was sent in the chat
//                                "       (select coalesce(message.message, '<no-messages-found>') " +
//                                "               from Message as message " +
//                                "              where message.chat.id = chat.id " +
//                                "                and message.persistDate = ( " +
//                                "                       select max (message.persistDate) " +
//                                "                             from Message as message " +
//                                "                            where chat.id = message.chat.id))," +
//                                // Same with persist date of the message
//                                "       (select coalesce(message.persistDate, '0001-01-01 00:00:00') " +
//                                "               from Message as message " +
//                                "              where message.chat.id = chat.id" +
//                                "                and message.persistDate = ( " +
//                                "                       select max (message.persistDate) " +
//                                "                             from Message as message " +
//                                "                            where chat.id = message.chat.id)))" +
//                                "     from Chat as chat " +
//                                "left join GroupChat as groupChat " +
//                                "       on chat.id = groupChat.id " +
//                                "left join SingleChat as singleChat " +
//                                "       on chat.id = singleChat.id " +
//                                "left join User as user1 " +
//                                "       on singleChat.userOne.id = user1.id " +
//                                "left join User as user2 " +
//                                "       on singleChat.useTwo.id = user2.id ",
//                        ChatDto.class)
//
//                .setParameter("group", ChatType.GROUP)
//                .setParameter("nameChat", nameChat)
//                .setParameter("userId", userId)
//                .getResultList();
//
        System.out.println("Udalos");

        return  null;

    }


}


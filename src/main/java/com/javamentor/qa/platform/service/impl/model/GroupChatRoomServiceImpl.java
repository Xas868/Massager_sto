package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;
import com.javamentor.qa.platform.dao.abstracts.model.UserDao;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.webapp.controllers.exceptions.AuthUserNotAuthorCreateGroupChatException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.DeleteGlobalChatException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class GroupChatRoomServiceImpl extends ReadWriteServiceImpl<GroupChat,Long> implements GroupChatRoomService {

    private final GroupChatRoomDao groupChatRoomDao;

    private final UserDao userDao;

    public GroupChatRoomServiceImpl(GroupChatRoomDao groupChatRoomDao, UserDao userDao) {
        super(groupChatRoomDao);
        this.groupChatRoomDao = groupChatRoomDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void deleteUserFromGroupChatById(Long chatId, Long userId) {
        User userAuthen = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GroupChat groupChat = groupChatRoomDao.getById(chatId).get();
        User userAuthor = groupChat.getUserAuthor();
        if (!userAuthen.getId().equals(userAuthor.getId()) && !userAuthen.getId().equals(userId)) {
            throw new AuthUserNotAuthorCreateGroupChatException("Удалять пользователей может только автор чата");
        }
        if (userId.equals(userAuthor.getId())) {
            groupChatRoomDao.deleteAllChat(chatId);
        }

        Set<User> users = groupChat.getUsers();
        for (User user : users) {
            if (user.getId().equals(userDao.getById(userId).get().getId()) && !userAuthen.getId().equals(userAuthor.getId())) {
                groupChatRoomDao.deleteUserFromGroupChatById(chatId, userId);
            }
        }
        if (groupChatRoomDao.getById(chatId).get().getChat().isGlobal()) {
            throw new DeleteGlobalChatException("Вы пытаетесь удалить глобальный чат");
        }
    }


    @Override
    public Optional<GroupChat> getGroupChatAndUsers(long id) {
        return groupChatRoomDao.getGroupChatAndUsers(id);
    }
}


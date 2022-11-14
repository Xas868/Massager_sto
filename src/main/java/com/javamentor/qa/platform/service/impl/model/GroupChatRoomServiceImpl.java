package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;
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

    public GroupChatRoomServiceImpl(GroupChatRoomDao groupChatRoomDao) {
        super(groupChatRoomDao);
        this.groupChatRoomDao = groupChatRoomDao;
    }

    @Override
    @Transactional
    public void deleteUserFromGroupChatById(Long chatId, Long userId) {
        User userAuthen = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (groupChatRoomDao.getById(chatId).get().getChat().isGlobal()) {
            throw new DeleteGlobalChatException("Вы пытаетесь удалить глобальный чат");
        }
        if (!userAuthen.getId().equals(groupChatRoomDao.getById(chatId).get().getUserAuthor().getId()) && !userAuthen.getId().equals(userId)) {
            throw new AuthUserNotAuthorCreateGroupChatException("Удалять пользователей может только автор чата");
        }
        Set<User> users = groupChatRoomDao.getById(chatId).get().getUsers();
        Long idUserAuthor = groupChatRoomDao.getById(chatId).get().getUserAuthor().getId();
        for (User user : users) {
            if (user.getId().equals(userId) && !userAuthen.getId().equals(idUserAuthor)) {
                groupChatRoomDao.deleteUserFromGroupChatById(chatId, userId);
            }
        }
        if (userId.equals(groupChatRoomDao.getById(chatId).get().getUserAuthor().getId())) {
            groupChatRoomDao.deleteAllChat(chatId);
        }
    }


    @Override
    public Optional<GroupChat> getGroupChatAndUsers(long id) {
        return groupChatRoomDao.getGroupChatAndUsers(id);
    }
}


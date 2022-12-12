package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupChatRoomDao;
import com.javamentor.qa.platform.exception.GroupChatException;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.GroupChatRoomService;
import com.javamentor.qa.platform.models.entity.chat.GroupChat;
import com.javamentor.qa.platform.webapp.controllers.exceptions.AuthUserNotAuthorCreateGroupChatException;
import com.javamentor.qa.platform.webapp.controllers.exceptions.DeleteGlobalChatException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        boolean isUserAuthor = groupChatRoomDao.isUserAuthor(chatId, userId);
        User userAuthen = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GroupChat gc = getGroupChatAndUsers(chatId).get();
        if (gc.getChat().isGlobal()) {
            throw new DeleteGlobalChatException("Вы пытаетесь удалить глобальный чат");
        }
        if (isUserAuthor) { // если автор чата
            if (userAuthen.getId().equals(userId)) { //авторизированный юзер передает себя то удаляет все полностью
                gc.setUsers(null);
                update(gc);
                groupChatRoomDao.deleteById(chatId);
            }
        }// иначе не автор
        if (!userAuthen.getId().equals(userId)) { // пытается удалить другого то исключение
            throw new AuthUserNotAuthorCreateGroupChatException("Удалять пользователей может только автор чата");
        } //пытается удалить себя то удаляет из чата только себя
        groupChatRoomDao.deleteUserFromGroupChatById(chatId, userId);



    }

    @Override
    public Optional<GroupChat> getGroupChatAndUsers(long id) {
        return groupChatRoomDao.getGroupChatAndUsers(id);
    }

    @Transactional
    public void updateImageGroupChat(long id, String image) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<GroupChat> groupChatOptional = groupChatRoomDao.getById(id);
        if (!groupChatOptional.isPresent()) {
            throw new GroupChatException("The group chat doesn`t exist. Sorry boy");
        }
        GroupChat groupChat = groupChatOptional.get();
        if (groupChat.getUserAuthor().getId() != user.getId()){
            throw new GroupChatException("You aren`t author of group chat. Author can update image only. Sorry boy");
        }
        groupChat.setImage(image);
        groupChatRoomDao.update(groupChat);
    }


}


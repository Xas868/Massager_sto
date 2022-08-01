package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends ReadWriteDao<User, Long> {

    Optional<User> getWithRoleByEmail(String email);

    List<Long> checkExistsUserById(List<Long> ids);

    void changePassword(String password, String username);

    boolean isUserExistByEmail(String email);

    void deleteByName(String email);

}

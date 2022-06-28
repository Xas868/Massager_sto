package com.javamentor.qa.platform.dao.impl.model.model;

import com.javamentor.qa.platform.models.entity.user.Role;

import java.util.Optional;

public interface RoleDao extends ReadWriteDao<Role, Long> {

    Optional<Role> getByName(String name);
}

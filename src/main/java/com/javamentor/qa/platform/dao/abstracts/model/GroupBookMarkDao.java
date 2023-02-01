package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.dto.UserProfileGroup;

import java.util.List;

public interface GroupBookMarkDao extends ReadWriteDao<GroupBookmark, Long> {
    List<UserProfileGroup> getAllUserBookMarkGroupNamesByUserId(Long id);

    boolean isGroupBookMarkExistsByName(Long id, String title);
}
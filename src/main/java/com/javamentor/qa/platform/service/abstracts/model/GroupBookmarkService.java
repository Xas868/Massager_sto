package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.models.dto.UserProfileGroup;

import java.util.List;


public interface GroupBookmarkService extends ReadWriteService<GroupBookmark, Long> {
    List<UserProfileGroup> getAllUserBookMarkGroupNamesByUserId(Long id);

    boolean isGroupBookMarkExistsByName(Long id, String title);
}
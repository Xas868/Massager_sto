package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.GroupBookmark;

import java.util.List;

public interface GroupBookmarkService extends ReadWriteService<GroupBookmark, Long> {
    List<String> getAllUserBookMarkGroupNamesByUserId(Long id);

    boolean isGroupBookMarkExistsByName(Long id, String title);
}
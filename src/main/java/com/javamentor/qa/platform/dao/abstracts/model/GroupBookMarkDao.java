package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.GroupBookmark;

import java.util.List;

public interface GroupBookMarkDao extends ReadWriteDao<GroupBookmark,Long> {
    List<String> getAllUserBookMarkGroupNames(Long id);
}
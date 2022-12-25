package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookMarkDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import com.javamentor.qa.platform.service.abstracts.model.GroupBookmarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupBookmarkServiceImpl extends ReadWriteServiceImpl<GroupBookmark, Long> implements GroupBookmarkService {
    private final GroupBookMarkDao groupBookMarkDao;

    public GroupBookmarkServiceImpl(GroupBookMarkDao groupBookMarkDao) {
        super(groupBookMarkDao);
        this.groupBookMarkDao = groupBookMarkDao;
    }

    @Override
    public List<String> getAllUserBookMarkGroupNamesByUserId(Long id) {
        return groupBookMarkDao.getAllUserBookMarkGroupNamesByUserId(id);
    }

    @Override
    public boolean isGroupBookMarkExistsByName(Long id, String title) {
        return groupBookMarkDao.isGroupBookMarkExistsByName(id, title);
    }
}
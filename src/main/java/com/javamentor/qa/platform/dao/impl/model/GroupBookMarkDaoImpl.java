package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.GroupBookMarkDao;
import com.javamentor.qa.platform.models.entity.GroupBookmark;
import org.springframework.stereotype.Repository;

@Repository
public class GroupBookMarkDaoImpl extends ReadWriteDaoImpl<GroupBookmark, Long> implements GroupBookMarkDao {
}
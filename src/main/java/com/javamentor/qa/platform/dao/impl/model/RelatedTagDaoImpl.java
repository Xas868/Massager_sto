package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.impl.model.model.RelatedTagDao;
import com.javamentor.qa.platform.models.entity.question.RelatedTag;
import org.springframework.stereotype.Repository;

@Repository
public class RelatedTagDaoImpl extends ReadWriteDaoImpl<RelatedTag, Long> implements RelatedTagDao {

}

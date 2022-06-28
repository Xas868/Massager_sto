package com.javamentor.qa.platform.dao.impl.model.model;

import com.javamentor.qa.platform.models.entity.question.TrackedTag;

public interface TrackedTagDao extends ReadWriteDao<TrackedTag, Long> {

    void deleteTrackedTagByTagIdAndUserId(Long tagId, Long userId);

    boolean existsByTagIdAndUserId(Long tagId, Long userId);
}

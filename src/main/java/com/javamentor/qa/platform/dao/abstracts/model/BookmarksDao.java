package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.bookmark.BookMarks;

public interface BookmarksDao extends ReadWriteDao<BookMarks, Long> {

    boolean findBookmarksByUserAndQuestion(Long userId, Long questionId);
}
package com.javamentor.qa.platform.dao.abstracts.model;

import com.javamentor.qa.platform.models.entity.bookmark.BookMarks;

import java.util.Optional;

public interface BookmarksDao extends ReadWriteDao<BookMarks, Long> {

    boolean findBookmarksByUserAndQuestion(Long userId, Long questionId);


    Optional<BookMarks> getBookmarkByQuestionIdAndUserId(Long userId, Long questionId);
}
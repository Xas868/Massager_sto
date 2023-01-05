package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.bookmark.BookMarks;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;

import java.util.Optional;

public interface BookmarksService extends ReadWriteService<BookMarks, Long> {

    void addQuestionInBookmarks(User user, Question question);

    Optional <BookMarks> getBookmarkByQuestionId(Long userId, Long questionId);
}
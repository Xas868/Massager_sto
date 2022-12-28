package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.entity.bookmark.SortBookmark;

import java.util.List;

public interface BookMarksDtoDao {
    List<BookMarksDto> getAllBookMarksInUserProfile(Long id, SortBookmark sortBookmark);
}

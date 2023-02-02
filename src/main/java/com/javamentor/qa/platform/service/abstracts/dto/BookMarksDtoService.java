package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.entity.bookmark.SortBookmark;

import java.util.List;

public interface BookMarksDtoService extends PageDtoService<BookMarksDto> {
    List<BookMarksDto> getAllBookMarksInUserProfile(Long id, SortBookmark sortBookmark);
}

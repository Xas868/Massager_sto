package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.BookMarksDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.entity.bookmark.SortBookmark;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.BookMarksDtoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookMarksDtoServiceImpl extends DtoServiceImpl<BookMarksDto> implements BookMarksDtoService {

    private final BookMarksDtoDao bookMarksDtoDao;
    private final TagDtoDao tagDtoDao;

    public BookMarksDtoServiceImpl(BookMarksDtoDao bookMarksDtoDao, TagDtoDao tagDtoDao, Map<String, PageDtoDao<BookMarksDto>> stringPageDtoDaoMap) {
        super(stringPageDtoDaoMap);
        this.bookMarksDtoDao = bookMarksDtoDao;
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public List<BookMarksDto> getAllBookMarksInUserProfile(Long id, SortBookmark sortBookmark) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<BookMarksDto> resultList = bookMarksDtoDao.getAllBookMarksInUserProfile(user.getId(), sortBookmark);
        var map = tagDtoDao.getTagDtoByQuestionIds(
                resultList.stream()
                        .map(BookMarksDto::getQuestionId)
                        .collect(Collectors.toList()));
       resultList.forEach(q -> q.setListTagDto(map.containsKey(q.getQuestionId())
                ? map.get(q.getQuestionId())
                : new ArrayList<>()));
        return resultList;
    }

    @Override
    public PageDTO<BookMarksDto> getPageDto(PaginationData properties) {
        var pageDto = super.getPageDto(properties);
        var tagDtoByQuestionIds =
                tagDtoDao.getTagDtoByQuestionIds(pageDto.getItems()
                        .stream()
                        .map(BookMarksDto::getQuestionId)
                        .collect(Collectors.toList()));
        pageDto.getItems().forEach(bookMarksDto ->
                bookMarksDto.setListTagDto(tagDtoByQuestionIds.get(bookMarksDto.getQuestionId())));

        return pageDto;
    }
}

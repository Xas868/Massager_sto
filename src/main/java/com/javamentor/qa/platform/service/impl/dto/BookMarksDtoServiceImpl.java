package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.BookMarksDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.models.dto.BookMarksDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.BookMarksDtoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookMarksDtoServiceImpl implements BookMarksDtoService {

    private final BookMarksDtoDao bookMarksDtoDao;
    private final TagDtoDao tagDtoDao;

    public BookMarksDtoServiceImpl(BookMarksDtoDao bookMarksDtoDao, TagDtoDao tagDtoDao) {
        this.bookMarksDtoDao = bookMarksDtoDao;
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public List<BookMarksDto> getAllBookMarksInUserProfile(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<BookMarksDto> resultList = bookMarksDtoDao.getAllBookMarksInUserProfile(user.getId());
        var map = tagDtoDao.getTagDtoByQuestionIds(
                resultList.stream().map(BookMarksDto::getQuestionId).collect(Collectors.toList()));
        resultList.forEach(q -> q.setListTagDto(map.containsKey(q.getQuestionId()) ? map.get(q.getQuestionId()) : new ArrayList<>()));
        return resultList;
    }
}

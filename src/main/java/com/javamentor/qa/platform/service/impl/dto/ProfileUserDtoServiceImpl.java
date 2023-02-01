package com.javamentor.qa.platform.service.impl.dto;
import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.service.abstracts.dto.ProfileUserDtoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProfileUserDtoServiceImpl extends DtoServiceImpl<UserProfileQuestionDto> implements ProfileUserDtoService {
    private final TagDtoDao tagDtoDao;

    public ProfileUserDtoServiceImpl(Map<String, PageDtoDao<UserProfileQuestionDto>> stringPageDtoDaoMap, TagDtoDao tagDtoDao) {
        super(stringPageDtoDaoMap);
        this.tagDtoDao = tagDtoDao;
    }
    @Override
    public PageDTO<UserProfileQuestionDto> getPageDto(PaginationData properties) {
        PageDTO<UserProfileQuestionDto> pageDTO = super.getPageDto(properties);
        List<UserProfileQuestionDto> items = pageDTO.getItems();
        var map = tagDtoDao.getTagDtoByQuestionIds(
                items.stream().map(UserProfileQuestionDto::getQuestionId).collect(Collectors.toList())
        );
        items.forEach(q ->
                q.setListTagDto(map.containsKey(q.getQuestionId()) ? map.get(q.getQuestionId()) : new ArrayList<>()));

        return pageDTO;
    }
}

package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserProfileDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.dto.UserProfileAnswerDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.answer.ProfileAnswerSort;
import com.javamentor.qa.platform.service.abstracts.dto.UserProfileDtoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserProfileDtoServiceImpl extends DtoServiceImpl<UserProfileAnswerDto> implements UserProfileDtoService {
    private final UserProfileDtoDao userProfileDtoDao;

    public UserProfileDtoServiceImpl(Map<String, PageDtoDao<UserProfileAnswerDto>> stringPageDtoDaoMap, UserProfileDtoDao userProfileDtoDao) {
        super(stringPageDtoDaoMap);
        this.userProfileDtoDao = userProfileDtoDao;
    }

    @Override
    public PageDTO<UserProfileAnswerDto> getPageDto(PaginationData properties) {
        return super.getPageDto(properties);
    }

    @Override
    public List<UserProfileAnswerDto> getAllUserProfileAnswerDtoByIdAndSort(Long id, ProfileAnswerSort profileAnswerSort) {
        return userProfileDtoDao.getAllUserProfileAnswerDtoById(id).stream()
                .sorted(profileAnswerSort.getComparator())
                .collect(Collectors.toList());
    }
}
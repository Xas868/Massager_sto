package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.TagDtoDao;
import com.javamentor.qa.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserProfileQuestionDto;
import com.javamentor.qa.platform.models.dto.UserProfileVoteDto;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.models.entity.question.ProfileQuestionSort;
import com.javamentor.qa.platform.models.util.CalendarPeriod;
import com.javamentor.qa.platform.service.abstracts.dto.UserDtoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserDtoServiceImpl extends DtoServiceImpl<UserDto> implements UserDtoService {
    private final UserDtoDao userDtoDao;
    private final TagDtoDao tagDtoDao;

    public UserDtoServiceImpl(UserDtoDao userDtoDao, Map<String, PageDtoDao<UserDto>> daoMap, TagDtoDao tagDtoDao) {
        super(daoMap);
        this.userDtoDao = userDtoDao;
        this.tagDtoDao = tagDtoDao;
    }

    @Override
    public Optional<UserDto> findUserDtoById(Long id) {
        Optional<UserDto> user = userDtoDao.findUserDto(id);
        user.ifPresent(userDto -> userDto.setListTagDto(tagDtoDao.getTop3TagsForUser(id)));
        return user;
    }

    @Override
    public List<UserProfileQuestionDto> getUserProfileQuestionDtoByUserIdIsDeleted(Long id) {
        List<UserProfileQuestionDto> resultList = userDtoDao.getAllUserProfileQuestionDtoByUserIdWhereQuestionIsDeleted(id);
        var map = tagDtoDao.getTagDtoByQuestionIds(
                resultList.stream().map(UserProfileQuestionDto::getQuestionId).collect(Collectors.toList())
        );
        resultList.forEach(q -> q.setListTagDto(map.containsKey(q.getQuestionId()) ? map.get(q.getQuestionId()) : new ArrayList<>()));
        return resultList;
    }

    @Override
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoById(Long id) {
        List<UserProfileQuestionDto> resultList = userDtoDao.getAllUserProfileQuestionDtoById(id);
        var map = tagDtoDao.getTagDtoByQuestionIds(
                resultList.stream().map(UserProfileQuestionDto::getQuestionId).collect(Collectors.toList())
        );
        resultList.forEach(q ->
                q.setListTagDto(map.containsKey(q.getQuestionId()) ? map.get(q.getQuestionId()) : new ArrayList<>()));
        return resultList;
    }

    @Override
    public List<UserProfileQuestionDto> getAllUserProfileQuestionDtoByIdAndSort(Long id, ProfileQuestionSort profileQuestionSort) {
        return getAllUserProfileQuestionDtoById(id).stream()
                .sorted(profileQuestionSort.getComparator())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getTopUsersForDaysRankedByNumberOfQuestions(CalendarPeriod calendarPeriod) {
        var resultList = userDtoDao.getTopUsersForDaysRankedByNumberOfQuestions(calendarPeriod);
        var usersIdList = resultList.stream().map(UserDto::getId).collect(Collectors.toList());
        var map = usersIdList.stream().collect(Collectors.toMap(Function.identity(), tagDtoDao::getTop3TagsForUser));
        resultList.forEach(u -> u.setListTagDto(map.get(u.getId())));
        return resultList;
    }

    @Override
    public PageDTO<UserDto> getPageDto(PaginationData properties) {
        var pageDto = super.getPageDto(properties);
        var usersIdList = pageDto.getItems().stream().map(UserDto::getId).collect(Collectors.toList());
        var map = usersIdList.stream().collect(Collectors.toMap(Function.identity(), tagDtoDao::getTop3TagsForUser));
        pageDto.getItems().forEach(u -> u.setListTagDto(map.get(u.getId())));
        return pageDto;
    }

    @Override
    public Long getCountAnswersPerWeekByUserId(Long userId) {
        return userDtoDao.getCountAnswersPerWeekByUserId(userId);
    }

    @Override
    public List<Long> getUnregisteredUserIds(List<Long> userIds) {
        return userDtoDao.getUnregisteredUserIds(userIds);
    }


    @Override
    public List<UserProfileVoteDto> getCountVotesAnswersAndQuestions(Long id){
        return userDtoDao.getCountVotesAnswersAndQuestions(id);
    }


}

package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.PopularTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.dto.question.TagViewDto;

import java.util.List;

public interface TagDtoService extends PageDtoService<TagViewDto>{
    List<TagDto> getIgnoredTagsByUserId(Long userId);
    List<TagDto> getTrackedTagsByUserId(Long userId);
    List<Long> getIgnoredTagsIdByUserId(Long userId);
    List<Long> getTrackedTagsIdByUserId(Long userId);
    List<PopularTagDto> getPopularTags();
    List<PopularTagDto> getPopularTags(Integer limit);
    List<TagDto> getTagDtoServiceById(Long id);
    List<TagDto> getTagsLike(String value);
    List<TagDto> getTop3TagsForUser(Long userId);
}

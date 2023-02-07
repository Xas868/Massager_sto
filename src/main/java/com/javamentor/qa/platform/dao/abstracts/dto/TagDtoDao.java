package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.question.PopularTagDto;
import com.javamentor.qa.platform.models.dto.TagDto;
import java.util.List;
import java.util.Map;

public interface TagDtoDao {
    List<TagDto> getIgnoredTagsByUserId(Long userId);
    List<TagDto> getTrackedTagsByUserId(Long userId);
    List<TagDto> getIgnoredTagsIdByUserId(Long userId);
    List<TagDto> getTrackedTagsIdByUserId(Long userId);

    List<PopularTagDto> getPopularTags();
    List<PopularTagDto> getPopularTags(Integer limit);
    List<TagDto> getTagDtoDaoById(Long id);
    List<TagDto> getTagsLike(String value);
    List<TagDto> getTop3TagsForUser(Long userId);
    Map<Long, List<TagDto>> getTagDtoByQuestionIds(List<Long> questionIds);
}


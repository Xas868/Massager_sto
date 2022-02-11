package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.dto.question.QuestionCommentDto;
import com.javamentor.qa.platform.models.dto.question.TagDto;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String title;
    private Long authorId;
    private Long authorReputation; //(можно подсчитать с помощью sql);
    private String authorName;
    private String authorImage;
    private String description;
    private int viewCount; //(пока не считай это поле, как оно будет считаться решим позже, пусть пока будет 0)
    private int countAnswer;// (можно подсчитать с помощью sql);
    private int countValuable; // (Это голоса за ответ QuestionVote);
    private LocalDateTime persistDateTime;
    private LocalDateTime lastUpdateDateTime;
    private List<TagDto> listTagDto;
    private List<QuestionCommentDto> listQuestionCommentDto;
    private VoteType isUserVote;
}

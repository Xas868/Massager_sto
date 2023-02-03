package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileCommentDto {
    private Long id;
    private String commentText;
    private LocalDateTime persistDate;
    private Long answerId;
    private Long questionId;
    private CommentType commentType;

    @Override
    public String toString() {
        return "UserProfileCommentDto{" +
               "id=" + id +
               ", comment=" + commentText +
               ", persistDate=" + persistDate +
               ", questionId=" + questionId +
               ", answerId=" + answerId +
               ", commentType=" + commentType +
               '}';
    }
}

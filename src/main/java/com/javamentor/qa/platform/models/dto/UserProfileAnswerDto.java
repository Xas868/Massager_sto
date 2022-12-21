package com.javamentor.qa.platform.models.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileAnswerDto {
    private Long answerId;
    private String title;
    private Long view;
    private Long vote;
    private Long questionId;
    private LocalDateTime persistDateTime;
}
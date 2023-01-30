package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor

public class UserProfileReputationDto {
    private int countReputation;
    private String questionTitle;
    private Long questionId;
    private Long answerId;
    private LocalDateTime persistDate;
    private ReputationType reputationType;



    public UserProfileReputationDto(int countReputation, Long questionId, String questionTitle, Long answerId, LocalDateTime persistDate, ReputationType reputationType) {
        this.countReputation = countReputation;
        this.questionTitle = questionTitle;
        this.questionId = questionId;
        this.answerId = answerId;
        this.persistDate = persistDate;
        this.reputationType = reputationType;
    }
}


package com.javamentor.qa.platform.models.entity.user.reputation;

import com.javamentor.qa.platform.models.dto.RoleChatStatus;

public enum ReputationType {
    Question,
    Answer,
    VOTE_UP_ANSWER,
    VOTE_DOWN_ANSWER,
    VOTE_UP_CREATE_QUESTION,
    VOTE_DOWN_CREATE_QUESTION;

    public static ReputationType fromStringToEnum(String reputationType) {
        return (reputationType == null ? null : ReputationType.valueOf(reputationType));
    }
    public static ReputationType fromIntegerToEnum(Integer reputationType) {
        return (reputationType == null ? null : ReputationType.values()[reputationType]);
    }
}

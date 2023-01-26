package com.javamentor.qa.platform.models.entity.user.reputation;

public enum ReputationType {
    Question (5),
    Answer (5),
    VOTE_UP_ANSWER (10),
    VOTE_DOWN_ANSWER (-5),
    VOTE_UP_QUESTION (10),
    VOTE_DOWN_QUESTION (-5);
    private int value;

    public int getValue() {
        return value;
    }

    ReputationType(int value) {
        this.value = value;
    }

}

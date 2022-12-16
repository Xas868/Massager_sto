package com.javamentor.qa.platform.models.dto;

public enum RoleChatStatus {
    AUTHOR, MODERATOR;

    public static RoleChatStatus fromStringToEnum(String roleChatStatus) {
        return (roleChatStatus == null ? null : RoleChatStatus.valueOf(roleChatStatus));
    }
}

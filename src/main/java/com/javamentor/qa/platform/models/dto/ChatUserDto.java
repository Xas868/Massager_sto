package com.javamentor.qa.platform.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChatUserDto {
    private Long userId;
    private String fullName;
    private String linkImage;
    private RoleChatStatus roleChatStatus;

    public ChatUserDto(Long userId, String fullName, String linkImage, String roleChatStatus) {
        this.userId = userId;
        this.fullName = fullName;
        this.linkImage = linkImage;
        this.roleChatStatus = RoleChatStatus.fromStringToEnum(roleChatStatus);

    }
}

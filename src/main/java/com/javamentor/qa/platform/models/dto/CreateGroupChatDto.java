package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupChatDto {

    private String chatName;

    private List<Long> userIds;
}

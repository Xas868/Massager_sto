package com.javamentor.qa.platform.models.dto;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupChatDto {

    private String chatName;

    private List<Long> userIds;
}

package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long id;
    private String name;
    private String image;
    private String lastMessage;
    private LocalDateTime persistDateTimeLastMessage;
}

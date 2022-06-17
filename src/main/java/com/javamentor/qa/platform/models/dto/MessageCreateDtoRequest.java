package com.javamentor.qa.platform.models.dto;





import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
import lombok.Builder;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MessageCreateDtoRequest implements Serializable {



    private String message;
    private Long senderId;
    private Long chatId;
    private String senderNickname;
    private String senderImage;



}

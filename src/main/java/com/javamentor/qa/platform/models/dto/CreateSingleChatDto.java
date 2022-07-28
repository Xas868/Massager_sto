package com.javamentor.qa.platform.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Component
public class CreateSingleChatDto {
    @NotBlank(message = "Это поле не может быть пустым")
    @NotNull(message = "Это поле не может быть null")
    private Long userId;
    @NotBlank(message = "Это поле не может быть пустым")
    @NotNull(message = "Это поле не может быть null")
    private String message;
}

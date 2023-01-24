package com.javamentor.qa.platform.models.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupBookmarkDto {
    @NotNull(message = "Это поле не может быть null")
    private long id;
    @NotNull(message = "Это поле не может быть null")
    private String title;
}

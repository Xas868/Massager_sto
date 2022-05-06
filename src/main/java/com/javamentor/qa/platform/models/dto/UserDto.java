package com.javamentor.qa.platform.models.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String fullName;
    private String imageLink;
    private String city;
    private Long reputation;
    private List<TagDto> listTagDto;

    public UserDto(Long id, String email, String fullName, String imageLink, String city, Long reputation) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.imageLink = imageLink;
        this.city = city;
        this.reputation = reputation;
    }
}

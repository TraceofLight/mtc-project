package com.soez.mtc.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserProfileInformationDto {
    private Long userIndex;
    private String userUid;
    private String userNickname;
    private String userProfilePictureSource;
    private Long userFollowCount;
    private Integer userArticleCount;

}

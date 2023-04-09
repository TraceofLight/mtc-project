package com.soez.mtc.relation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowingUserDto {

    private Long followIndex;
    private Long userIndex;
    private String userNickname;

    private String userPictureSource;


}

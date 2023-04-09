package com.soez.mtc.relation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter

public class FollowUserDto {

    private Long followIndex;
    private Long userIndex;
    private String userNickname;
    private String userPictureSource;


    @Builder
    public FollowUserDto(Long followIndex,Long userIndex,String userNickname,String userPictureSource){
        this.followIndex=followIndex;
        this.userIndex=userIndex;
        this.userNickname =userNickname;
        this.userPictureSource=userPictureSource;
    }
}

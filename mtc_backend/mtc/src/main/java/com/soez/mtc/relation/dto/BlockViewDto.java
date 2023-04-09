package com.soez.mtc.relation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockViewDto {
    //블락한 유저의 정보만 나옴
    private Long userIndex;
    private String userPictureSource;
    private String userNickName;

    @Builder
    public BlockViewDto(Long userIndex, String userPictureSource, String userNickName){
        this.userIndex=userIndex;
        this.userPictureSource=userPictureSource;
        this.userNickName=userNickName;
    }
}

package com.soez.mtc.relation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowViewDto {
    //타겟 유저는 안나오고 팔로우한 유저만 나옴
    private Long userIndex;
    private String userPictureSource;
    private String userNickName;

    @Builder
    public FollowViewDto(Long userIndex, String userPictureSource, String userNickName){
        this.userIndex=userIndex;
        this.userPictureSource=userPictureSource;
        this.userNickName=userNickName;
    }

}

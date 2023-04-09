package com.soez.mtc.setting.dto;

import com.soez.mtc.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SettingDto {


    private Long settingIndex;
    private Long settingUserIndex;
    private Boolean settingDarkMode;
    private Boolean settingLeftHand;
    private Boolean settingIgnoreFollow;
    private Boolean settingIgnoreEvaluate;
    private Boolean settingIgnoreComment;
    private Boolean settingIgnoreReply;

    @Builder
    public SettingDto(Long settingIndex, Long settingUserIndex,Boolean settingDarkMode, Boolean settingLeftHand,Boolean settingIgnoreFollow,Boolean settingIgnoreEvaluate,Boolean settingIgnoreComment,Boolean settingIgnoreReply){
        this.settingIndex = settingIndex;
        this.settingUserIndex = settingUserIndex;
        this.settingDarkMode = settingDarkMode;
        this.settingLeftHand = settingLeftHand;
        this.settingIgnoreFollow = settingIgnoreFollow;
        this.settingIgnoreEvaluate = settingIgnoreEvaluate;
        this.settingIgnoreComment = settingIgnoreComment;
        this.settingIgnoreReply = settingIgnoreReply;
    }
}

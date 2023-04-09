package com.soez.mtc.setting.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SettingUpdateDto {

    private Long settingUserIndex;
    private Boolean settingDarkMode;
    private Boolean settingLeftHand;
    private Boolean settingIgnoreFollow;
    private Boolean settingIgnoreEvaluate;
    private Boolean settingIgnoreComment;
    private Boolean settingIgnoreReply;

    @Builder
    public SettingUpdateDto(Long settingUserIndex , Boolean settingDarkMode, Boolean settingLeftHand, Boolean settingIgnoreFollow, Boolean settingIgnoreEvaluate, Boolean settingIgnoreComment, Boolean settingIgnoreReply){
        this.settingUserIndex = settingUserIndex;
        this.settingDarkMode = settingDarkMode;
        this.settingLeftHand = settingLeftHand;
        this.settingIgnoreFollow = settingIgnoreFollow;
        this.settingIgnoreEvaluate = settingIgnoreEvaluate;
        this.settingIgnoreComment = settingIgnoreComment;
        this.settingIgnoreReply = settingIgnoreReply;
    }
}

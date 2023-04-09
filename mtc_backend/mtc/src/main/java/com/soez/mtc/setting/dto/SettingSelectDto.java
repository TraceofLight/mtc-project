package com.soez.mtc.setting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SettingSelectDto {


    private Boolean settingDarkMode;
    private Boolean settingLeftHand;
    private Boolean settingIgnoreFollow;
    private Boolean settingIgnoreEvaluate;
    private Boolean settingIgnoreComment;
    private Boolean settingIgnoreReply;

    @Builder
    public SettingSelectDto(Boolean settingDarkMode, Boolean settingLeftHand, Boolean settingIgnoreFollow, Boolean settingIgnoreEvaluate, Boolean settingIgnoreComment, Boolean settingIgnoreReply){
        this.settingDarkMode = settingDarkMode;
        this.settingLeftHand = settingLeftHand;
        this.settingIgnoreFollow = settingIgnoreFollow;
        this.settingIgnoreEvaluate = settingIgnoreEvaluate;
        this.settingIgnoreComment = settingIgnoreComment;
        this.settingIgnoreReply = settingIgnoreReply;
    }
}

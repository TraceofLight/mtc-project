package com.soez.mtc.setting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="setting")
public class SettingEntity {


    @Id
    @Column(name = "setting_index")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long settingIndex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setting_user_index")
    private UserEntity settingUserIndex;
    private Boolean settingDarkMode;
    private Boolean settingLeftHand;
    private Boolean settingIgnoreFollow;
    private Boolean settingIgnoreEvaluate;
    private Boolean settingIgnoreComment;
    private Boolean settingIgnoreReply;




    @Builder
    public SettingEntity(UserEntity settingUserIndex,Boolean settingDarkMode, Boolean settingLeftHand, Boolean settingIgnoreFollow,Boolean settingIgnoreComment, Boolean settingIgnoreEvaluate ,Boolean settingIgnoreReply ) {
        this.settingUserIndex = settingUserIndex;
        this.settingDarkMode = settingDarkMode;
        this.settingLeftHand = settingLeftHand;
        this.settingIgnoreFollow = settingIgnoreFollow;
        this.settingIgnoreEvaluate = settingIgnoreEvaluate;
        this.settingIgnoreComment = settingIgnoreComment;
        this.settingIgnoreReply = settingIgnoreReply;


    }














}




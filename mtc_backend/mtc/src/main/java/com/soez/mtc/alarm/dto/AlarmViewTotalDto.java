package com.soez.mtc.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AlarmViewTotalDto {

    private Long alarmUserIndex;
    private String alarmDtype;
    private LocalDateTime alarmTime;
    private Boolean alarmCheck;


    //--------A
    private Long alarmArticleIndex=null;
    private Integer alarmArticleMaxGood=null;
    private String alarmArticlePictureSource=null;

    //-------C
    private Long alarmCommentUserIndex=null;
    private String alarmCommentUserNickname=null;
    private String alarmCommentContent=null;
    private Long alarmCommentIndex=null;
    private Long alarmCommentArticleIndex=null;
    private String alarmCommentArticlePictureSource=null;

    //---------------R
    private Long alarmReplyIndex=null;
    private Long alarmReplyUserIndex=null;
    private String alarmReplyUserNickname=null;
    private String alarmReplyContent=null;
    private Long alarmReplyCommentIndex=null;
    private Long alarmReplyArticleIndex=null;
    private String alarmReplyArticlePictureSource=null;


    //------------------F
    private Long alarmFollowUserIndex=null;
    private String alarmFollowUserNickname=null;
    private String alarmFollowUserPictureSource=null;

}

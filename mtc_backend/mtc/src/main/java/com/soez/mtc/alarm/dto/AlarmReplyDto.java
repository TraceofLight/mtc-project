package com.soez.mtc.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmReplyDto extends AlarmDto {

    private Long alarmReplyIndex;
    private Long alarmReplyUserIndex;
    private String alarmReplyUserNickname;
    private String alarmReplyUserPictureSource;
    private String alarmReplyContent;
    private Long alarmReplyCommentIndex;
    private String alarmReplyCommentContent;
    private Long alarmReplyArticleIndex;
    private String alarmReplyArticlePictureSource;


    @Builder
    public AlarmReplyDto(Long alarmReplyIndex,Long alarmReplyUserIndex, String alarmReplyUserNickname,String alarmReplyUserPictureSource,String alarmReplyContent, Long alarmReplyCommentIndex,String alarmReplyCommentContent, Long alarmReplyArticleIndex, String alarmReplyArticlePictureSource) {
        this.alarmReplyIndex = alarmReplyIndex;
        this.alarmReplyUserIndex = alarmReplyUserIndex;
        this.alarmReplyUserNickname = alarmReplyUserNickname;
        this.alarmReplyUserPictureSource = alarmReplyUserPictureSource;
        this.alarmReplyContent = alarmReplyContent;
        this.alarmReplyCommentIndex = alarmReplyCommentIndex;
        this.alarmReplyCommentContent = alarmReplyCommentContent;
        this.alarmReplyArticleIndex = alarmReplyArticleIndex;
        this.alarmReplyArticlePictureSource =alarmReplyArticlePictureSource;

    }
}
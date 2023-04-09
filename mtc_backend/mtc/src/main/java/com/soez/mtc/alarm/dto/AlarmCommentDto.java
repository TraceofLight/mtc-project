package com.soez.mtc.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmCommentDto extends AlarmDto{

    private Long alarmCommentUserIndex;
    private String alarmCommentUserNickname;
    private String alarmCommentUserPictureSource;
    private String alarmCommentContent;
    private Long alarmCommentIndex;
    private Long alarmCommentArticleIndex;
    private String alarmCommentArticlePictureSource;


    @Builder
    public AlarmCommentDto(Long alarmCommentUserIndex,String alarmCommentUserNickname,String alarmCommentUserPictureSource, String alarmCommentContent, Long alarmCommentIndex,Long alarmCommentArticleIndex,String alarmCommentArticlePictureSource ) {
                this.alarmCommentUserIndex = alarmCommentUserIndex;
                this.alarmCommentUserNickname = alarmCommentUserNickname;
                this.alarmCommentUserPictureSource = alarmCommentUserPictureSource;
                this.alarmCommentContent = alarmCommentContent;
                this.alarmCommentIndex = alarmCommentIndex;
                this.alarmCommentArticleIndex = alarmCommentArticleIndex;
                this.alarmCommentArticlePictureSource = alarmCommentArticlePictureSource;
    }


}

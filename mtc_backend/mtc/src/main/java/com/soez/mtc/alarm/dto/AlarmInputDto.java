package com.soez.mtc.alarm.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AlarmInputDto {

    //알람을 받는 애
    private Long alarmUserIndex;
    private String alarmDtype;
    private LocalDateTime alarmTime;
    private Long alarmFollowUserIndex;
    private Long alarmArticleIndex;
    private Long alarmCommentIndex;
    private Long alarmReplyIndex;

    @Builder
    public AlarmInputDto(Long alarmUserIndex,String alarmDtype,LocalDateTime alarmTime,Long alarmFollowUserIndex,Long alarmArticleIndex,Long alarmCommentIndex,Long alarmReplyIndex) {
        this.alarmUserIndex = alarmUserIndex;
        this.alarmDtype = alarmDtype;
        this.alarmTime = alarmTime;
        this.alarmFollowUserIndex = alarmFollowUserIndex;
        this.alarmArticleIndex = alarmArticleIndex;
        this.alarmCommentIndex = alarmCommentIndex;
        this.alarmReplyIndex = alarmReplyIndex;
    }


}

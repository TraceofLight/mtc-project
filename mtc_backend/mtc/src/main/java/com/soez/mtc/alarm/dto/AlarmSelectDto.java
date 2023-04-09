package com.soez.mtc.alarm.dto;

import lombok.*;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmSelectDto {

    @Id
    private Long alarmIndex;
    private Long alarmUserIndex;
    private String alarmDtype;
    private LocalDateTime alarmTime;
    private Long alarmFollowUserIndex;
    private Long alarmEvaluateIndex;
    private Long alarmCommentIndex;
    private Long alarmReplyIndex;
    private Boolean alarmCheck;

    @Builder
    public AlarmSelectDto(Long alarmIndex, Long alarmUserIndex, String alarmDtype, LocalDateTime alarmTime, Long alarmFollowUserIndex, Long alarmEvaluateIndex, Long alarmCommentIndex, Long alarmReplyIndex,Boolean alarmCheck) {
        this.alarmIndex = alarmIndex;
        this.alarmUserIndex = alarmUserIndex;
        this.alarmDtype = alarmDtype;
        this.alarmTime = alarmTime;
        this.alarmFollowUserIndex = alarmFollowUserIndex;
        this.alarmEvaluateIndex = alarmEvaluateIndex;
        this.alarmCommentIndex = alarmCommentIndex;
        this.alarmReplyIndex = alarmReplyIndex;
        this.alarmCheck = alarmCheck;
    }


}

package com.soez.mtc.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AlarmFollowDto extends AlarmDto{

    //알람을 받는 애
    private Long alarmFollowUserIndex;
    private String alarmFollowUserNickname;
    private String alarmFollowUserPictureSource;

    @Builder
    public AlarmFollowDto(Long alarmFollowUserIndex,String alarmFollowUserNickname, String alarmFollowUserPictureSource) {
                this.alarmFollowUserIndex = alarmFollowUserIndex;
                this.alarmFollowUserNickname = alarmFollowUserNickname;
                this.alarmFollowUserPictureSource = alarmFollowUserPictureSource;
    }


}

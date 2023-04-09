package com.soez.mtc.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class AlarmDto {

    private Long alarmIndex;
    //알람을 받는 애
    private String alarmDtype;
    private LocalDateTime alarmTime;
    private Boolean alarmCheck;





}

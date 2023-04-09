package com.soez.mtc.alarm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soez.mtc.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "alarm_dtype", discriminatorType = DiscriminatorType.STRING)
@Table(name="alarm")
public abstract class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="alarm_user_index")
    private UserEntity alarmUserIndex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime alarmTime;

    @Column(name="alarm_dtype",insertable = false,updatable = false)
    private String alarmDtype;

    private Boolean alarmCheck;

}











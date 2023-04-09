package com.soez.mtc.alarm.entity;

import com.soez.mtc.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("F")
@Setter
@Getter
public class AlarmFollowEntity extends AlarmEntity {

    @JoinColumn(name= "alarm_follow_user_index")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity alarmFollowUserIndex;

}

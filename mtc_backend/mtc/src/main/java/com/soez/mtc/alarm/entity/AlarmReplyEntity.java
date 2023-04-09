package com.soez.mtc.alarm.entity;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.reply.entity.ReplyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
@Setter
@Getter
public class AlarmReplyEntity extends AlarmEntity {

    @JoinColumn(name= "alarm_reply_index")
    @OneToOne(fetch = FetchType.LAZY)
    private ReplyEntity alarmReplyIndex;

}

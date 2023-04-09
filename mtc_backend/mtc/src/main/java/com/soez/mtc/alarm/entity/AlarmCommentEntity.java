package com.soez.mtc.alarm.entity;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("C")
@Setter
@Getter
public class AlarmCommentEntity extends AlarmEntity {

    @JoinColumn(name= "alarm_comment_index")
    @OneToOne(fetch = FetchType.LAZY)
    private CommentEntity alarmCommentIndex;

}

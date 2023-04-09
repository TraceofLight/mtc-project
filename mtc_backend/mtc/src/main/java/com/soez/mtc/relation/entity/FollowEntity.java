package com.soez.mtc.relation.entity;

import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="follow")
public class FollowEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followIndex;

    //타겟이 받는쪽 그냥이 하는쪽
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follow_user_index")
    private UserEntity followUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follow_target_index")
    private UserEntity followTargetIndex;




}




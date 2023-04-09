package com.soez.mtc.alarm.repository;

import com.soez.mtc.alarm.entity.AlarmCommentEntity;
import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.alarm.entity.AlarmFollowEntity;
import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmFollowJpaRepository extends JpaRepository<AlarmFollowEntity, Long> {


    List<AlarmFollowEntity> findAllByAlarmUserIndex(UserEntity userIndex);

    AlarmFollowEntity findAlarmFollowEntityByAlarmIndex(Long alarmIndex);


    @Modifying
    @Query(value = "delete from AlarmFollowEntity a where a.alarmIndex =  :alarmIndex")
    void deleteAlarmByAlarmIndex(@Param("alarmIndex") Long alarmIndex);

    AlarmFollowEntity findAlarmFollowEntityByAlarmFollowUserIndexAndAlarmUserIndex(UserEntity user1,UserEntity user2);


    //전체 삭제

    void deleteAlarmFollowEntityByAlarmUserIndexAndAlarmFollowUserIndex(UserEntity user1, UserEntity user2);

}

package com.soez.mtc.alarm.repository;

import com.soez.mtc.alarm.entity.AlarmCommentEntity;
import com.soez.mtc.alarm.entity.AlarmFollowEntity;
import com.soez.mtc.alarm.entity.AlarmReplyEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmReplyJpaRepository extends JpaRepository<AlarmReplyEntity, Long> {


    List<AlarmReplyEntity> findAllByAlarmUserIndex(UserEntity userIndex);

    AlarmReplyEntity findAlarmReplyEntityByAlarmIndex(Long alarmIndex);

    @Modifying
    @Query(value = "delete from AlarmReplyEntity a where a.alarmIndex =  :alarmIndex")
    void deleteAlarmByAlarmIndex(@Param("alarmIndex") Long alarmIndex);

}

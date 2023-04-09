package com.soez.mtc.alarm.repository;

import com.soez.mtc.alarm.entity.AlarmArticleEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmArticleJpaRepository extends JpaRepository<AlarmArticleEntity, Long> {

    List<AlarmArticleEntity> findAllByAlarmUserIndex(UserEntity userIndex);

    AlarmArticleEntity findAlarmArticleEntityByAlarmIndex(Long alarmIndex);

    @Modifying
    @Query(value = "delete from AlarmArticleEntity a where a.alarmIndex = :alarmIndex")
    void deleteAlarmByAlarmIndex(@Param("alarmIndex") Long alarmIndex);
}
package com.soez.mtc.alarm.repository;

import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.alarm.entity.AlarmFollowEntity;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {

    long countAlarmEntitiesByAlarmUserIndexAndAlarmCheckIsFalse(UserEntity alarmUserIndex);

    AlarmEntity findAlarmEntityByAlarmIndex(Long alarmIndex);

    void deleteAllByAlarmUserIndex(UserEntity userEntity);

}

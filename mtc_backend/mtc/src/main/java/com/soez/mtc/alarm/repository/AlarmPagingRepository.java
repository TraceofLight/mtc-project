package com.soez.mtc.alarm.repository;

import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlarmPagingRepository extends PagingAndSortingRepository<AlarmEntity, Long> {
    Page<AlarmEntity> findByAlarmUserIndex(UserEntity user, Pageable pageable);
}

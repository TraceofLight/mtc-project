package com.soez.mtc.setting.repository;

import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingJpaRepository extends JpaRepository<SettingEntity, Long> {

    SettingEntity findBySettingUserIndex(UserEntity userIndex);

}

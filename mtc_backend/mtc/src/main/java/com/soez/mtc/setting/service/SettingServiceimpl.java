package com.soez.mtc.setting.service;

import com.soez.mtc.setting.dto.SettingDto;
import com.soez.mtc.setting.dto.SettingSelectDto;
import com.soez.mtc.setting.dto.SettingUpdateDto;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.repository.SettingJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettingServiceimpl implements SettingService {

    @Autowired
    SettingJpaRepository settingJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Override
    public SettingDto createSetting(Long userIndex) throws Exception {

        SettingEntity settingEntity = SettingEntity.builder().
                settingUserIndex(userJpaRepository.findById(userIndex).get()).
                settingDarkMode(false).
                settingLeftHand(false).
                settingIgnoreFollow(false).
                settingIgnoreEvaluate(false).
                settingIgnoreComment(false).
                settingIgnoreReply(false).
                build();

        SettingDto settingDto = SettingDto.builder().
                settingUserIndex(userIndex).
                settingDarkMode(false).
                settingLeftHand(false).
                settingIgnoreFollow(false).
                settingIgnoreEvaluate(false).
                settingIgnoreComment(false).
                settingIgnoreReply(false).
                build();


        settingJpaRepository.save(settingEntity);

        return settingDto;

    }

    @Override
    public SettingSelectDto selectSetting(Long userIndex) throws Exception {
        UserEntity userEntity = userJpaRepository.findById(userIndex).get();
        SettingEntity settingEntity= settingJpaRepository.findBySettingUserIndex(userEntity);
        SettingSelectDto settingSelectDto = SettingSelectDto.builder().
                settingDarkMode(settingEntity.getSettingDarkMode()).
                settingLeftHand(settingEntity.getSettingLeftHand()).
                settingIgnoreFollow(settingEntity.getSettingIgnoreFollow()).
                settingIgnoreEvaluate(settingEntity.getSettingIgnoreEvaluate()).
                settingIgnoreComment(settingEntity.getSettingIgnoreComment()).
                settingIgnoreReply(settingEntity.getSettingIgnoreReply()).
                build();
        return settingSelectDto;
    }

    @Transactional
    @Override
    public SettingSelectDto updateSetting(SettingUpdateDto settingUpdateDto) throws Exception {

        UserEntity userEntity = userJpaRepository.findById(settingUpdateDto.getSettingUserIndex()).get();
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        settingEntity.setSettingDarkMode(false);
        settingEntity.setSettingLeftHand(false);
        settingEntity.setSettingIgnoreComment(settingUpdateDto.getSettingIgnoreComment());
        settingEntity.setSettingIgnoreEvaluate(settingUpdateDto.getSettingIgnoreEvaluate());
        settingEntity.setSettingIgnoreFollow(settingUpdateDto.getSettingIgnoreFollow());
        settingEntity.setSettingIgnoreReply(settingUpdateDto.getSettingIgnoreReply());

        //output을 위한 settingDto셋팅
        SettingSelectDto settingSelectDto = SettingSelectDto.builder().
                settingLeftHand(settingUpdateDto.getSettingLeftHand()).
                settingDarkMode(settingUpdateDto.getSettingDarkMode()).
                settingIgnoreComment(settingUpdateDto.getSettingIgnoreComment()).
                settingIgnoreEvaluate(settingUpdateDto.getSettingIgnoreEvaluate()).
                settingIgnoreFollow(settingUpdateDto.getSettingIgnoreFollow()).
                settingIgnoreReply(settingUpdateDto.getSettingIgnoreReply()).
                build();

        return settingSelectDto;
    }


}

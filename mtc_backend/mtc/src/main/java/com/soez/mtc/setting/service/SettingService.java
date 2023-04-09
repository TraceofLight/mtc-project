package com.soez.mtc.setting.service;

import com.soez.mtc.setting.dto.SettingDto;
import com.soez.mtc.setting.dto.SettingSelectDto;
import com.soez.mtc.setting.dto.SettingUpdateDto;
import com.soez.mtc.setting.entity.SettingEntity;

public interface SettingService {

    SettingDto createSetting(Long userIndex) throws Exception;

    SettingSelectDto selectSetting(Long userIndex)throws Exception;

    SettingSelectDto updateSetting(SettingUpdateDto settingUpdateDto) throws Exception;
}

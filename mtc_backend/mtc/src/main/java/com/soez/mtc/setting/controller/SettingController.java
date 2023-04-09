package com.soez.mtc.setting.controller;

import com.soez.mtc.alarm.service.AlarmService;
import com.soez.mtc.relation.dto.BlockDto;
import com.soez.mtc.relation.dto.BlockUserDto;
import com.soez.mtc.setting.dto.SettingDto;
import com.soez.mtc.setting.dto.SettingSelectDto;
import com.soez.mtc.setting.dto.SettingUpdateDto;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.service.SettingService;
import com.soez.mtc.user.dto.UserCreateDto;
import com.soez.mtc.user.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/swagger-ui.html
@RestController
@RequestMapping("/setting")
@Api("세팅 API")
@RequiredArgsConstructor
public class SettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    public SettingController(SettingService settingService) {this.settingService = settingService;}


    //Create
    @Transactional
    @ApiOperation(value = "회원 가입시 설정이 초기화 된다.", notes = "회원 설정을 한다.'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping("/createsetting/{user-index}")
    public ResponseEntity<?> createSetting(@PathVariable("user-index") Long userIndex) throws Exception {
        return new ResponseEntity<SettingDto>(settingService.createSetting(userIndex), HttpStatus.OK);
    }
    //Select
    @Transactional
    @ApiOperation(value = "회원 세팅을 조회한다.", notes = "회원 세팅을 조회한다.'success' 또는 'fail' 문자열을 반환한다.")
    @GetMapping("/selectsetting/{user-index}")
    public ResponseEntity<?> selectSetting(@PathVariable("user-index") Long userIndex) throws Exception {
        return new ResponseEntity<SettingSelectDto>(settingService.selectSetting(userIndex), HttpStatus.OK);
    }


    //Update
    @Transactional
    @ApiOperation(value="회원 세팅을 수정한다" , notes = "회원 설정을 수정한다.'success' 또는 'fail' 문자열을 반환한다." )
    @PutMapping("/updatesetting")
    public ResponseEntity<?> updateSetting(@RequestBody @ApiParam(value = "세팅 정보.", required = true) SettingUpdateDto settingUpdateDto) throws Exception {
        return new ResponseEntity<SettingSelectDto>(settingService.updateSetting(settingUpdateDto), HttpStatus.OK);
    }


}

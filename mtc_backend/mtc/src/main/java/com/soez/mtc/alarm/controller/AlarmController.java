package com.soez.mtc.alarm.controller;

import com.soez.mtc.alarm.dto.AlarmDto;
import com.soez.mtc.alarm.dto.AlarmInputDto;
import com.soez.mtc.alarm.dto.AlarmSelectDto;
import com.soez.mtc.alarm.dto.AlarmViewTotalDto;
import com.soez.mtc.alarm.service.AlarmService;
import com.soez.mtc.relation.dto.BlockDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/swagger-ui.html
@RestController
@RequestMapping("/alarm")
@Api("알람 API")
@RequiredArgsConstructor
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {this.alarmService = alarmService;}

    //팔로우 알람 ==============================================================================================
    @Transactional
    @ApiOperation(value = "팔로우 알람을 보낸다.", notes = "팔로우 알람 보내기. 'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping("/createfollowalarm/{follow-index}")
    public ResponseEntity<?> createFollowAlarm(@PathVariable("follow-index") Long followIndex) throws Exception {
        return new ResponseEntity<AlarmInputDto>(alarmService.createFollowAlarm(followIndex), HttpStatus.OK);
    }


    //댓글 알람 ==============================================================================================
    @Transactional
    @ApiOperation(value = "댓글 알람을 보낸다.", notes = "댓글 알람 보내기. 'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping("/createcommentalarm/{comment-index}")
    public ResponseEntity<?> createCommentAlarm(@PathVariable("comment-index") Long commentIndex) throws Exception {
        return new ResponseEntity<AlarmInputDto>(alarmService.createCommentAlarm(commentIndex), HttpStatus.OK);
    }


    //답글 알람 ==============================================================================================
    @Transactional
    @ApiOperation(value = "답글 알람을 보낸다.", notes = "답글 알람 보내기. 'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping("/createreplyalarm/{reply-index}")
    public ResponseEntity<?> createReplyAlarm(@PathVariable("reply-index") Long replyIndex) throws Exception {
        return new ResponseEntity<AlarmInputDto>(alarmService.createReplyAlarm(replyIndex), HttpStatus.OK);
    }


    //알람 삭제 =========================================================================================================
    @Transactional
    @ApiOperation(value = "알람을 삭제한다.", notes = " 알람을 삭제한다. 'success' 또는 'fail' 문자열을 반환한다.")
    @DeleteMapping("/deletealarm")
    public void deleteAlarm(@RequestBody @ApiParam(value = "알람 정보.", required = true) AlarmSelectDto alarmSelectDto) throws Exception {
            alarmService.deleteAlarm(alarmSelectDto);
    }


    //알람 조회============================================================================================


    @Transactional
    @ApiOperation(value = "내 알람 목록을 본다.", notes = "알람 목록 보기.'success' 또는 'fail' 문자열을 반환한다.")
    @GetMapping("/selectAlarm/{user-index}")
    public ResponseEntity<?> selectAlarm(@PathVariable("user-index") Long userIndex) throws Exception {
        return new ResponseEntity<List<AlarmDto>>(alarmService.selectAlarm(userIndex), HttpStatus.OK);
    }

    //알람 조회 with Paging 예아
    @Transactional
    @ApiOperation(value = "내 알람 목록을 본다. 페이징으로", notes = "알람 목록 보기.'success' 또는 'fail' 문자열을 반환한다. 페이지 숫자는 0부터 시작한다.")
    @GetMapping("/selectAlarmWithPaging/{user-index}&{page-number}")
    public ResponseEntity<Page<AlarmViewTotalDto>> selectAlarmWithPaging(@PathVariable("user-index") Long userIndex, @PathVariable("page-number")Long pageNum) throws Exception {
        Page<AlarmViewTotalDto> alarmViewTotalDtos=alarmService.readAlarmWithPaging(userIndex,pageNum.intValue(),20);
        if(alarmViewTotalDtos.hasContent()){
            return new ResponseEntity<Page<AlarmViewTotalDto>>(alarmViewTotalDtos,HttpStatus.OK);
        }
        return new ResponseEntity<Page<AlarmViewTotalDto>>(alarmViewTotalDtos,HttpStatus.NO_CONTENT);
    }

    @Transactional
    @ApiOperation(value = "알람 개수 확인", notes="확인하지 않은 알람 개수를 본다.")
    @GetMapping("/countAlarm/{user-index}")
    public ResponseEntity<?> countAlarm(@PathVariable("user-index") Long userIndex) throws Exception{
        return new ResponseEntity<Long>(alarmService.countAlarm(userIndex), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "알람 전체 삭제", notes="확인하지 않은 알람 개수를 본다.")
    @DeleteMapping("/deleteAlarmAll/{user-index}")
    public void deleteAlarmAll(@PathVariable("user-index") Long userIndex) throws Exception{
        alarmService.deleteAlarmAll(userIndex);
    }

}

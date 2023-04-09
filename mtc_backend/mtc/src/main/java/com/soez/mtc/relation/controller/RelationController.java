package com.soez.mtc.relation.controller;

import com.soez.mtc.alarm.service.AlarmService;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.relation.dto.*;
import com.soez.mtc.relation.entity.BlockEntity;

import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;

import com.soez.mtc.relation.service.RelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/relation")
@RestController
@Api(tags={"User의 팔로우, 차단에 관한 api"})
public class RelationController {

    @Autowired
    private RelationService relationService;

    @Autowired
    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }




    @Transactional
    @ApiOperation(value = "회원을 차단한다.", notes = "회원을 차단한다.'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping("/createblock")
    public ResponseEntity<?> createBlock(@RequestBody @ApiParam(value = "회원 정보.", required = true) BlockDto blockDto) throws Exception {
        return new ResponseEntity<BlockDto>(relationService.createBlock(blockDto), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "회원 차단 목록을 본다.", notes = "회원 차단 목록 보기.'success' 또는 'fail' 문자열을 반환한다.")
    @GetMapping("/selectblocklist/{user-index}")
    public ResponseEntity<?> selectBlockList(@PathVariable("user-index") Long userIndex) throws Exception {
        return new ResponseEntity<List<BlockUserDto>>(relationService.selectBlockUserList(userIndex), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "회원 차단을 취소한다.", notes = "회원 차단 목록 보기.'success' 또는 'fail' 문자열을 반환한다.")
    @DeleteMapping("/deleteblock")
    public void deleteBlock(@RequestBody @ApiParam(value = "회원 정보.", required = true) BlockDto blockDto) throws Exception {
        relationService.deleteBlock(blockDto);
    }

    @Transactional
    @ApiOperation(value = "회원을 팔로잉 한다.", notes = "회원을 팔로잉한다.'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping("/createfollow")
    public ResponseEntity<?> createFollow(@RequestBody @ApiParam(value = "회원 정보.", required = true) FollowDto followDto) throws Exception {
        return new ResponseEntity<FollowDto>(relationService.createFollow(followDto), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value="내 팔로우 목록을 본다.", notes = "팔로우 목록을 본다.")
    @GetMapping("/selectfollowlist/{user-index}")
    public ResponseEntity<?> selectFollowList(@PathVariable("user-index") Long userIndex) throws Exception{
        return new ResponseEntity<List<FollowUserDto>>(relationService.selectFollowUserList(userIndex), HttpStatus.OK);
    }


    @Transactional
    @ApiOperation(value="팔로잉을 취소 한다.", notes = "팔로우 취소")
    @DeleteMapping("/deletefollow")
    public void deleteFollow(@RequestBody @ApiParam(value = "회원 정보.", required = true) FollowDto followDto) throws Exception {
        relationService.deleteFollow(followDto);
    }

    @Transactional
    @ApiOperation(value="나를 팔로우한 사람" , notes = "나를 팔로우한 사람들을 본다.")
    @GetMapping("/selectfollowinglist/{user-index}")
    public ResponseEntity<?> selectFollowingList(@PathVariable("user-index") Long userIndex) throws Exception{
        return new ResponseEntity<List<FollowingUserDto>>(relationService.selectFollowingUserList(userIndex), HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value="나를 팔로우한 사람을 찾기 with Paging" , notes = "나를 팔로우한 사람들을 본다. 그것도 페이징으로!")
    @GetMapping("/selectFollowingListPage/{user-index}&{page-number}")
    public ResponseEntity<Page<FollowViewDto>> selectFollowingListPage(@PathVariable("user-index") Long userIndex, @PathVariable("page-number") @ApiParam(value = "페이지 번호, 0번부터 시작이다!", required = true) Long pageNumber) throws Exception{
        Page<FollowViewDto> followingUserDtoPage=relationService.readFollwingUserPage(userIndex, Long.valueOf(pageNumber).intValue(),20);

        if(followingUserDtoPage.hasContent()){
            return new ResponseEntity<Page<FollowViewDto>>(followingUserDtoPage,HttpStatus.OK);
        }
        return new ResponseEntity<Page<FollowViewDto>>(followingUserDtoPage,HttpStatus.NO_CONTENT);
    }


    @Transactional
    @ApiOperation(value="내가 차단한 사람목록을 보기 with Paging" , notes = "내가 차단한 사람들을 본다. 그것도 페이징으로!")
    @GetMapping("/selectBlockListPage/{user-index}&{page-number}")
    public ResponseEntity<Page<BlockViewDto>> selectBlockListPage(@PathVariable("user-index") Long userIndex, @PathVariable("page-number") @ApiParam(value = "페이지 번호, 0번부터 시작이다!", required = true) Long pageNumber) throws Exception{
        Page<BlockViewDto> blockViewDtos=relationService.readBlockUserPage(userIndex,Long.valueOf(pageNumber).intValue(),20);

        if(blockViewDtos.hasContent()){
            return new ResponseEntity<Page<BlockViewDto>>(blockViewDtos,HttpStatus.OK);
        }
        return new ResponseEntity<Page<BlockViewDto>>(blockViewDtos,HttpStatus.NO_CONTENT);

    }

    @Transactional
    @ApiOperation(value="팔로우 여부를 판단한다." , notes = "true false를 내뱉어줄게요오")
    @GetMapping("/checkFollow")
    public ResponseEntity<?> checkFollow(@RequestParam(value = "user-index") Long userIndex, @RequestParam(value = "target-index") Long targetIndex) throws Exception {
        return new ResponseEntity<Boolean>(relationService.checkFollow(userIndex, targetIndex),HttpStatus.OK);

    }

//    @Transactional
//    @ApiOperation(value="유저 팔로우 수 보기" , notes = "팔로우 수를 본다.")
//    @GetMapping("/selectFollowCount/{user-index}")
//    public ResponseEntity<?> selectFollowCount(@PathVariable("user-index")Long userIndex) throws Exception{
//        return new ResponseEntity<Long>(relationService.selectFollowCount(userIndex),HttpStatus.OK);
//    }


}

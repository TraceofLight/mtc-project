package com.soez.mtc.user.controller;

import com.soez.mtc.article.service.ArticleService;
import com.soez.mtc.relation.service.RelationService;
import com.soez.mtc.s3.S3Service;
import com.soez.mtc.user.dto.*;
import com.soez.mtc.user.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import com.soez.mtc.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@Api(tags={"User 정보를 제공하는 controller"})
public class UserController {

    private UserService userService;
    private S3Service s3Service;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RelationService relationService;

    @Autowired
    public UserController(UserService userService, S3Service s3Service) {
        this.userService = userService;
        this.s3Service = s3Service;
    }


    @Transactional
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.'success' 또는 'fail' 문자열을 반환한다.")
    @PostMapping ("/register")
    public ResponseEntity<UserInformationDto> registUser(@ApiParam(value = "회원 정보.", required = true) UserCreateDto userCreateDto, @RequestParam(value="userMultipartFile", required = false) MultipartFile multipartFile) throws Exception {
        logger.info("userFile : {}", multipartFile);
        logger.info("userUid : {}", userCreateDto.getUserUid());
        logger.info("userNickname : {}", userCreateDto.getUserNickname());
        String url = "";
        if(multipartFile != null){
            url = s3Service.uploadFile(multipartFile, "user");
        }

        try {
            UserEntity user = userService.createUserEntity(userCreateDto, url);
            return new ResponseEntity<>(new UserInformationDto(user.getUserIndex(), user.getUserUid(), user.getUserNickname(), user.getUserPictureSource()), HttpStatus.OK);
        } catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // 프사 수정은 얘기해보기로
    @Transactional
    @ApiOperation(value = "회원 프로필 수정", notes = "회원 수정을 한다.'success' 또는 'fail' 문자열을 반환한다.")
    @PutMapping ("/updateProfile")
    public ResponseEntity<String> updateUserProfile(@RequestParam(value="userMultipartFile", required = false) MultipartFile multipartFile) throws  Exception {

        logger.info("file : {}", multipartFile);
        String url = "";
        if(multipartFile != null){
            url = s3Service.uploadFile(multipartFile, "user");
        }

        try {
            String userUid = userService.getCurrentUserUid();
            UserEntity user = userService.updateUserProfile(userUid, url);
            logger.info("user.index {}", user.getUserIndex());
            return new ResponseEntity<>(user.getUserPictureSource(), HttpStatus.OK);
        } catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Transactional
    @ApiOperation(value = "회원 닉네임 수정", notes = "회원 수정을 한다.'success' 또는 'fail' 문자열을 반환한다.")
    @PutMapping ("/updateNickname")
    public ResponseEntity<String> updateUserNickname(@RequestBody UserNicknameDto userNicknameDto) throws  Exception {
        String userNickname = userNicknameDto.getUserNickname();
        String userUid = userNicknameDto.getUserUid();
        logger.info("nickName : {}", userNickname);
        logger.info("nickUid : {}", userUid);

        try {
            UserEntity user = userService.updateUserNickname(userUid, userNickname);
            logger.info("user.index {}", user.getUserIndex());
            return new ResponseEntity<>(user.getUserNickname(), HttpStatus.OK);
        } catch (IOException e){
            e.printStackTrace();
            logger.info("error {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Transactional
    @ApiOperation(value = "회원 탈퇴", notes = "회원이 탈퇴한다.'success' 또는 'fail' 문자열을 반환한다.")
    @DeleteMapping ("/delete/{user-index}")
    public ResponseEntity<String> deleteUser(@PathVariable("user-index") Long userIndex){
        if(userService.getCurrentUserUid().equals(userService.readUserEntityByUserIndex(userIndex).getUserUid())) {
            userService.deleteUserEntityByUserIndex(userIndex);
            return new ResponseEntity<>("삭제했습니다", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("삭제 실패했습니다", HttpStatus.EXPECTATION_FAILED);
        }

    }

    @Transactional
    @ApiOperation(value = "회원 조회 - by uid", notes = "회원을 조회한다.'success' 또는 'fail' 문자열을 반환한다.")
    @GetMapping("/information/{user-uid}")
        public ResponseEntity<UserInformationDto> userInformationByUid(@PathVariable("user-uid") String userUid){
        UserEntity user = null;
        logger.info("userUid : {}", userUid);

        userService.getCurrentUserUid();
        if(userUid != null) {
            user = userService.readUserEntityByUserUid(userUid);
        }
        if(user != null) {
            UserInformationDto userInformationDto = new UserInformationDto(user.getUserIndex(), user.getUserUid(), user.getUserNickname(), user.getUserPictureSource());
            logger.info("UserIndex, {}", userInformationDto.getUserIndex());
            logger.info("UserNickname, {}", userInformationDto.getUserNickname());
            logger.info("UserPictureSource, {}", userInformationDto.getUserProfilePictureSource());
            return new ResponseEntity<>(userInformationDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @ApiOperation(value = "회원 조회 - by index", notes = "회원을 조회한다.'success' 또는 'fail' 문자열을 반환한다.")
    @GetMapping("/information/index/{user-index}")
    public ResponseEntity<UserProfileInformationDto> userInformationByIndex(@PathVariable("user-index") Long userIndex) throws Exception {
        UserEntity user = null;

        logger.info("userIndex : {}", userIndex);

//        userService.getCurrentUserUid();
        if(userIndex != null) {
            user = userService.readUserEntityByUserIndex(userIndex);
        }
        if(user != null) {
            UserProfileInformationDto userProfileInformationDto = new UserProfileInformationDto(user.getUserIndex(), user.getUserUid(), user.getUserNickname(), user.getUserPictureSource(), relationService.selectFollowCount(userIndex), user.getArticleEntities().size());
            logger.info("UserUid, {}", userProfileInformationDto.getUserUid());
            logger.info("UserNickname, {}", userProfileInformationDto.getUserNickname());
            logger.info("UserPictureSource, {}", userProfileInformationDto.getUserProfilePictureSource());
            return new ResponseEntity<>(userProfileInformationDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @GetMapping("/nicknameCheck/{user-nickName}")
    @ApiOperation(value = "닉네임 조회", notes = "닉네임을 조회한다.'success' 또는 'fail' 문자열을 반환한다.")
    public ResponseEntity<Boolean> userNicknameCheck(@PathVariable("user-nickName") String userNickName) throws Exception {
        if(!userService.readUserEntityByUserNickname(userNickName)){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @Transactional
    @ApiOperation(value = "회원 유무 조회", notes = "회원을 유무를 조회한다.'success' 또는 'fail' 문자열을 반환한다.")
    @GetMapping("/userCheck/{user-uid}")
    public ResponseEntity<Boolean> userCheckByUid(@PathVariable("user-uid") String userUid){
        UserEntity user = null;
        logger.info("userUid : {}", userUid);
        if(userUid != null) {
            user = userService.readUserEntityByUserUid(userUid);
        }
        if(user != null) {
            logger.info("UserIndex, {}", user.getUserIndex());
            logger.info("UserNickname, {}", user.getUserNickname());
            logger.info("UserPictureSource, {}", user.getUserPictureSource());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

}

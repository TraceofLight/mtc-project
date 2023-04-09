package com.soez.mtc.user.service;

import com.soez.mtc.setting.dto.SettingDto;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.repository.SettingJpaRepository;
import com.soez.mtc.user.dto.UserCreateDto;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserJpaRepository userJpaRepository;
    private final ServletContext servletContext;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final SettingJpaRepository settingJpaRepository;

    @Override
    public UserEntity createUserEntity(UserCreateDto userCreateDto, String url) throws Exception {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserNickname(userCreateDto.getUserNickname());

        if(userEntity == null){
            userEntity = UserEntity.builder()
                    .userUid(userCreateDto.getUserUid())
                    .userNickname(userCreateDto.getUserNickname())
                    .userPictureSource(url)
                    .userReportedCount(0L)
                    .build();

            userJpaRepository.save(userEntity);
        }else{
            logger.info("이미 유저 존재");
            return null;
        }
        //=============유저 초기 세팅해주기=============
        createSetting(userEntity.getUserIndex());
        //==========================================
        return userEntity;
    }

    @Override
    public UserEntity updateUserProfile(String userUid, String url) throws Exception {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserUid(userUid);

        if(userEntity != null) {
            userEntity.setUserPictureSource(url);
        }else{
            logger.info("유저가 정보를 찾을 수 없습니다");
            return null;
        }

        return userJpaRepository.save(userEntity);
    }

    @Override
    public UserEntity updateUserNickname(String userUid, String userNickname) throws Exception {
        UserEntity userEntity = userJpaRepository.findUserEntityByUserUid(userUid);

        if(userEntity != null) {
            userEntity.setUserNickname(userNickname);
        }else{
            logger.info("유저가 정보를 찾을 수 없습니다");
            return null;
        }

        return userJpaRepository.save(userEntity);
    }

    @Override
    public Boolean readUserEntityByUserNickname(String userNickname) throws Exception {
        return userJpaRepository.existsByUserNickname(userNickname);
    }

    @Override
    public UserEntity readUserEntityByUserIndex(Long userIndex) {
        return userJpaRepository.findUserEntityByUserIndex(userIndex);
    }

    @Override
    public UserEntity readUserEntityByUserUid(String userUid) {
        return userJpaRepository.findUserEntityByUserUid(userUid);
    }

    @Override
    public void deleteUserEntityByUserIndex(Long userIndex) {
        String realPath = servletContext.getRealPath("/user");

        logger.info("절대 경로 : {}", realPath);
        logger.info("현재 경로 : {}", System.getProperty("user.dir"));

        String saveFolder = realPath + File.separator + userIndex;

        File folder = new File(saveFolder);

        if(folder.exists()){
            File[] files = folder.listFiles();

            for(int i=0; i<files.length; i++){
                if( files[i].delete() ){
                    logger.info("{} 삭제 성공", files[i].getName());
                }else{
                    logger.info("{} 삭제 실패", files[i].getName());
                }
            }

            folder.delete();
            logger.info("{} 삭제 성공", folder.getName());
        }

        userJpaRepository.deleteUserEntityByUserIndex(userIndex);
    }

    //=======================회원가입시 세팅=================================


    public void createSetting(Long userIndex) throws Exception {

        logger.info("userIndex : {}", userIndex);
        logger.info("userIndex : {}", userJpaRepository.findById(userIndex).get());

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


    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userUid) throws UsernameNotFoundException {
        UserDetails user = userJpaRepository.findUserEntityByUserUid(userUid);
        if(user == null) return null;
        else return user;
    }

    @Override
    @Transactional
    public String getCurrentUserUid(){

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("user 정보 : {}", user.getUserUid());

        String userUid = user.getUserUid();

        if(userUid == null){
            logger.debug("Security Context 에 인증 정보가 없습니다");
            return null;
        }

        return userUid;
    }

    @Transactional
    @Override
    public int readUserArticleByUserIndex(Long userIndex){
        UserEntity user = userJpaRepository.findUserEntityByUserIndex(userIndex);

        return user.getArticleEntities().size();
    }
}

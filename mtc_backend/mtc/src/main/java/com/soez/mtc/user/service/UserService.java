package com.soez.mtc.user.service;

import com.soez.mtc.user.dto.UserCreateDto;
import com.soez.mtc.user.entity.UserEntity;


public interface UserService {
     UserEntity createUserEntity(UserCreateDto userDto, String url) throws Exception;

     Boolean readUserEntityByUserNickname(String userNickname) throws Exception;

     UserEntity readUserEntityByUserIndex(Long userIndex);

     UserEntity readUserEntityByUserUid(String userUid);

     UserEntity updateUserProfile(String userUid, String url) throws Exception;

     UserEntity updateUserNickname(String userUid, String userNickname) throws Exception;

     void deleteUserEntityByUserIndex(Long userIndex);

     String getCurrentUserUid();

     int readUserArticleByUserIndex(Long userIndex);
}

package com.soez.mtc.relation.service;

import com.soez.mtc.relation.dto.*;
import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.cglib.core.Block;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface RelationService {

    //차단
    BlockDto createBlock(BlockDto blockDto) throws Exception;


    List<BlockUserDto> selectBlockUserList(Long userIndex) throws Exception;

    void deleteBlock(BlockDto blockDto) throws Exception;


    // 팔로우
    FollowDto createFollow(FollowDto followDto) throws Exception;


    List<FollowUserDto> selectFollowUserList(Long userIndex) throws Exception;

    void deleteFollow(FollowDto followDto) throws Exception;

    List<FollowingUserDto> selectFollowingUserList(Long userIndex) throws Exception;

    Page<FollowViewDto> readFollwingUserPage(Long userIndex,int page, int size) throws Exception;

    Page<BlockViewDto> readBlockUserPage(Long userIndex,int page, int size) throws Exception;

    Boolean checkFollow(Long userIndex,Long targetIndex)throws Exception;

    Long selectFollowCount(Long userIndex) throws Exception;
}

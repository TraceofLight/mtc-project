package com.soez.mtc.relation.service;

import com.soez.mtc.alarm.dto.AlarmInputDto;
import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.alarm.entity.AlarmFollowEntity;
import com.soez.mtc.alarm.repository.AlarmFollowJpaRepository;
import com.soez.mtc.alarm.repository.AlarmJpaRepository;
import com.soez.mtc.relation.dto.*;
import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.relation.repository.BlockJpaRepository;
import com.soez.mtc.relation.repository.BlockPagingRepository;
import com.soez.mtc.relation.repository.FollowJpaRepository;
import com.soez.mtc.relation.repository.FollowingPagingRepository;
import com.soez.mtc.setting.entity.SettingEntity;
import com.soez.mtc.setting.repository.SettingJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RelationServiceimpl implements RelationService {

    @Autowired
    private BlockJpaRepository blockJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;


    @Autowired
    private FollowJpaRepository followJpaRepository;

    @Autowired
    private SettingJpaRepository settingJpaRepository;
    @Autowired
    private AlarmFollowJpaRepository alarmFollowJpaRepository;
    @Autowired
    private FollowingPagingRepository followingPagingRepository;

    @Autowired
    private AlarmJpaRepository alarmJpaRepository;
    @Autowired
    private BlockPagingRepository blockPagingRepository;


    @Override
    public BlockDto createBlock(BlockDto blockDto) throws Exception {

        
        //나는 나 자신 차단 못함 !
        if(blockDto.getBlockUserIndex()==blockDto.getBlockTargetIndex()) return null;

        BlockEntity blockEntity = new BlockEntity();
        UserEntity user1 = userJpaRepository.findById(blockDto.getBlockUserIndex()).get();
        UserEntity user2 = userJpaRepository.findById(blockDto.getBlockTargetIndex()).get();
        
        
        blockEntity.setBlockUserIndex(user1);
        blockEntity.setBlockTargetIndex(user2);

        //중복 안되게 체크
        Long checkBlock = blockJpaRepository.checkBlock(blockEntity);
        if(checkBlock!=null) return null;
        
        user1.getBlockTargetEntities().add(blockEntity);
        blockJpaRepository.save(blockEntity);

        //유저가 차단을 하면, follow도 끊어준다

        followJpaRepository.deleteFollowEntityByFollowUserIndexAndFollowTargetIndex(user1,user2);

        //유저가 있다면,


        return blockDto;

    }
    @Override
    public List<BlockUserDto> selectBlockUserList(Long UserIndex) throws Exception {

        UserEntity user = userJpaRepository.findById(UserIndex).get();

        List<BlockEntity> blockEntities = blockJpaRepository.userBlockList(user);

        List<BlockUserDto> blockUserList = new ArrayList<>();

        for(BlockEntity block : blockEntities){

            UserEntity targetUser = userJpaRepository.findById(block.getBlockTargetIndex().getUserIndex()).get();
            //blockUser = block.getBlockTargetIndex();
            BlockUserDto bud = new BlockUserDto(block.getBlockIndex(),targetUser.getUserIndex(), targetUser.getUserNickname(), targetUser.getUserPictureSource());

            blockUserList.add(bud);
        }
        return blockUserList;
    }
    @Override
    public void deleteBlock(BlockDto blockDto) throws Exception {


        UserEntity user1 = userJpaRepository.findById(blockDto.getBlockUserIndex()).get(); //차단 한 애
        UserEntity user2 = userJpaRepository.findById(blockDto.getBlockTargetIndex()).get(); //차단 당한 애

        blockJpaRepository.deleteBlockEntityByBlockUserIndexAndBlockTargetIndex(user1,user2);
    }

    @Override
    public FollowDto createFollow(FollowDto followDto) throws Exception {

        //나는 나 자신 팔로우 못함 !
        if(followDto.getFollowUserIndex()==followDto.getFollowTargetIndex()) return null;

        FollowEntity followEntity = new FollowEntity();

        UserEntity user1 = userJpaRepository.findById(followDto.getFollowUserIndex()).get();
        UserEntity user2 = userJpaRepository.findById(followDto.getFollowTargetIndex()).get();


        followEntity.setFollowUserIndex(user1);
        followEntity.setFollowTargetIndex(user2);

        //중복 안되게 체크
        Long checkFollow = followJpaRepository.checkFollow(followEntity);
        if(checkFollow!=null) return null;

        //알람을 위한 엔티티
        FollowEntity followAlarmEntity = followJpaRepository.save(followEntity);

        user1.getFollowTargetEntities().add(followEntity);

        //팔로우 하면 차단 사라짐
        blockJpaRepository.deleteBlockEntityByBlockUserIndexAndBlockTargetIndex(user1,user2);


        //알람 생성 ===========================================================
        createFollowAlarm(followAlarmEntity.getFollowIndex());
        //===================================================================
        return followDto;
    }

    @Override
    public List<FollowUserDto> selectFollowUserList(Long userIndex) throws Exception{

        UserEntity user = userJpaRepository.findById(userIndex).get();

        List<FollowEntity> followEntities = followJpaRepository.userFollowList(user);

        List<FollowUserDto> followList = new ArrayList<>();


        for(FollowEntity follow : followEntities){

            UserEntity targetUser = userJpaRepository.findById(follow.getFollowTargetIndex().getUserIndex()).get();

            FollowUserDto fud = FollowUserDto.builder().userIndex(targetUser.getUserIndex())
                                                       .userNickname(targetUser.getUserNickname())
                                                       .userPictureSource(targetUser.getUserPictureSource()).build();
            followList.add(fud);

        }

        return followList;

    }
    @Override
    public void deleteFollow(FollowDto followDto) throws Exception{

        UserEntity user1 = userJpaRepository.findById(followDto.getFollowUserIndex()).get(); //차단 한 애
        UserEntity user2 = userJpaRepository.findById(followDto.getFollowTargetIndex()).get(); //차단 당한 애

        FollowEntity followEntity = followJpaRepository.findByFollowUserIndexAndFollowTargetIndex(user1,user2);

        //팔로우 취소하면 알람 없앰;

        AlarmFollowEntity follow = alarmFollowJpaRepository.findAlarmFollowEntityByAlarmFollowUserIndexAndAlarmUserIndex(user1,user2);

       ;
        alarmFollowJpaRepository.deleteAlarmFollowEntityByAlarmUserIndexAndAlarmFollowUserIndex(user2,user1);

        user1.getFollowTargetEntities().remove(followEntity);

        followJpaRepository.deleteById(followEntity.getFollowIndex());



    }

    @Override
    public List<FollowingUserDto> selectFollowingUserList(Long userIndex) throws Exception{

        UserEntity user = userJpaRepository.findById(userIndex).get();

        List<FollowEntity> followEntities = followJpaRepository.userFollowingList(user);

        List<FollowingUserDto> followingList = new ArrayList<>();

        for(FollowEntity following : followEntities){
            //나를 팔로우한 애;
            UserEntity fromUser = userJpaRepository.findById(following.getFollowUserIndex().getUserIndex()).get();

            FollowingUserDto fud = new FollowingUserDto(following.getFollowIndex(),fromUser.getUserIndex(),fromUser.getUserNickname(),fromUser.getUserPictureSource());

            followingList.add(fud);
        }

        return followingList;

    }

    //====================================페이징==============================================
    @Override
    public Page<FollowViewDto> readFollwingUserPage(Long userIndex,int page, int size) throws Exception {
        UserEntity userEntity=userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<FollowEntity> followEntities= followingPagingRepository.findByFollowTargetIndex(userEntity, PageRequest.of(page,size, Sort.by("followIndex").descending()));
        List<FollowViewDto> followDtos=new ArrayList<>();
        for(FollowEntity followEntity:followEntities){
            FollowViewDto followViewDto=FollowViewDto.builder().
                    userIndex(followEntity.getFollowUserIndex().getUserIndex()).
                    userPictureSource(followEntity.getFollowUserIndex().getUserPictureSource()).
                    userNickName(followEntity.getFollowUserIndex().getUserNickname()).build();
            followDtos.add(followViewDto);
        }
        return new PageImpl<>(followDtos,followEntities.getPageable(),followEntities.getTotalElements());
    }

    @Override
    public Page<BlockViewDto> readBlockUserPage(Long userIndex,int page, int size) throws Exception {
        UserEntity userEntity=userJpaRepository.findUserEntityByUserIndex(userIndex);
        Page<BlockEntity> blockEntities=blockPagingRepository.findByBlockTargetIndex(userEntity,PageRequest.of(page,size, Sort.by("blockIndex").descending()));
        List<BlockViewDto> blockViewDtos =new ArrayList<>();
        for(BlockEntity block:blockEntities){
            BlockViewDto blockViewDto=BlockViewDto.builder().
                    userIndex(block.getBlockUserIndex().getUserIndex()).
                    userPictureSource(block.getBlockUserIndex().getUserPictureSource()).
                    userNickName(block.getBlockUserIndex().getUserNickname()).
                    build();
            blockViewDtos.add(blockViewDto);
        }
        return new PageImpl<>(blockViewDtos, blockEntities.getPageable(), blockEntities.getTotalElements());
    }

    @Override
    public Boolean checkFollow(Long userIndex, Long targetIndex) throws Exception {
        UserEntity user1 = userJpaRepository.findById(userIndex).get();
        UserEntity user2 = userJpaRepository.findById(targetIndex).get();
        return followJpaRepository.existsFollowEntitiesByFollowUserIndexAndFollowTargetIndex(user1, user2);
    }




    // 알람 생성 메소드=========================================================================

    public void createFollowAlarm(Long followIndex) throws Exception {
        //세팅 확인
        FollowEntity followEntity = followJpaRepository.findById(followIndex).get();
        //본인이면 return null
        if(followEntity.getFollowUserIndex()==followEntity.getFollowTargetIndex()) return;

        //타겟 유저의 엔티티를 불러옴
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(followEntity.getFollowTargetIndex().getUserIndex());
        //그 유저의 셋팅을 확인하기 위해 셋팅을 불러옴
        SettingEntity settingEntity = settingJpaRepository.findBySettingUserIndex(userEntity);
        //세팅 확인 //만약 거부해 놓는거면 안보낼거임;
        if(settingEntity.getSettingIgnoreFollow()) return;

        else{
            //팔로우를 신청한 사람
            UserEntity followerUserEntity = userJpaRepository.findById(followEntity.getFollowUserIndex().getUserIndex()).get();

            AlarmFollowEntity alarmFollowEntity = new AlarmFollowEntity();
            alarmFollowEntity.setAlarmUserIndex(userEntity);
            alarmFollowEntity.setAlarmFollowUserIndex(followerUserEntity);
            alarmFollowEntity.setAlarmTime(LocalDateTime.now());
            alarmFollowEntity.setAlarmCheck(Boolean.FALSE);

            alarmFollowJpaRepository.save(alarmFollowEntity);
            //output을 위한 followDto 생성
            AlarmInputDto alarmInputDto = AlarmInputDto.builder().
                    alarmUserIndex(userEntity.getUserIndex()).
                    alarmDtype("F").
                    alarmTime(alarmFollowEntity.getAlarmTime()).
                    alarmFollowUserIndex(followerUserEntity.getUserIndex()).
                    build();

        }
    }

    //follow한 사람 count 하기
    @Override
    public Long selectFollowCount(Long userIndex) throws Exception {
        UserEntity userEntity = userJpaRepository.findById(userIndex).get();
        return followJpaRepository.countByFollowTargetIndex(userEntity);
    }

}

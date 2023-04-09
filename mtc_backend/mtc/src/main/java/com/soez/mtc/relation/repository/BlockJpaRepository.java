package com.soez.mtc.relation.repository;

import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlockJpaRepository extends JpaRepository<BlockEntity, Long> {

    @Query(value = "select b from BlockEntity b where b.blockUserIndex = :userIndex")
    List<BlockEntity> userBlockList(@Param("userIndex") UserEntity userIndex);

    //차단에 있나 없나 검사 (팔로우로 부터)
    @Query(value = "select b.blockIndex from BlockEntity b where b.blockUserIndex = :#{#blockIndex.blockUserIndex} and b.blockTargetIndex = :#{#blockIndex.blockTargetIndex}")
    Long checkBlock(@Param(value="blockIndex") BlockEntity blockIndex);



    BlockEntity findByBlockUserIndexAndBlockTargetIndex(UserEntity blockUserIndex, UserEntity blockTargetIndex);


    void deleteBlockEntityByBlockUserIndexAndBlockTargetIndex(UserEntity blockUserIndex, UserEntity blockTagerIntex);




}

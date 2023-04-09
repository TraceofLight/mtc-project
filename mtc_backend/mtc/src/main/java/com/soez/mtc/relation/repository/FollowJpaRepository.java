package com.soez.mtc.relation.repository;

import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    //내가 팔로우 한 사람들
    @Query(value = "select f from FollowEntity f where f.followUserIndex = :userIndex")
    List<FollowEntity> userFollowList(@Param("userIndex") UserEntity userIndex);

    //나를 팔로우 한 사람들
    @Query(value = "select f from FollowEntity f where f.followTargetIndex = :userIndex")
    List<FollowEntity> userFollowingList(@Param("userIndex") UserEntity userIndex);


    //블락에 있나 없나 검사 (팔로우로 부터)
    @Query(value = "select f.followIndex from FollowEntity f where f.followUserIndex = :#{#followIndex.followUserIndex} and f.followTargetIndex = :#{#followIndex.followTargetIndex}")
    Long checkFollow(@Param(value="followIndex") FollowEntity followIndex);

    FollowEntity findByFollowUserIndexAndFollowTargetIndex(UserEntity followUserIndex, UserEntity followTargetIndex);

    void deleteFollowEntityByFollowUserIndexAndFollowTargetIndex(UserEntity followUserIndex, UserEntity followTargetIndex);

    Boolean existsFollowEntitiesByFollowUserIndexAndFollowTargetIndex(UserEntity user1, UserEntity user2);

    Long countByFollowTargetIndex(UserEntity targetIndex);
}

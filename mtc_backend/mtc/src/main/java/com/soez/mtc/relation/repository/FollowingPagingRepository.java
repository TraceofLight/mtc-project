package com.soez.mtc.relation.repository;

import com.soez.mtc.relation.entity.FollowEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface FollowingPagingRepository extends PagingAndSortingRepository<FollowEntity, Long> {
//    Page<FollowEntity> findByFollowTargetIndex_FollowIndex(UserEntity followTargetIndex, Pageable pageable)

    Page<FollowEntity> findByFollowTargetIndex(UserEntity followTargetIndex, Pageable pageable);
}

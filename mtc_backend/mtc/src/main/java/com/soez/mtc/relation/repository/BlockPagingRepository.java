package com.soez.mtc.relation.repository;

import com.soez.mtc.relation.entity.BlockEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlockPagingRepository extends PagingAndSortingRepository<BlockEntity, Long> {
    Page<BlockEntity> findByBlockTargetIndex(UserEntity blockTargetUser, Pageable pageable);
}

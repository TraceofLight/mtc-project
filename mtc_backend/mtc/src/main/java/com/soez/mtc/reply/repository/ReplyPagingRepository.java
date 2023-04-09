package com.soez.mtc.reply.repository;

import com.soez.mtc.reply.entity.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReplyPagingRepository extends PagingAndSortingRepository<ReplyEntity, Long> {
    Page<ReplyEntity> findByReplyCommentIndex_CommentIndex(Long commentIndex, Pageable pageable);
}

package com.soez.mtc.comment.repository;

import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentPagingRepository extends PagingAndSortingRepository<CommentEntity, Long> {
    Page<CommentEntity> findByCommentArticleIndex_ArticleIndex(Long articleIndex, Pageable pageable);


}

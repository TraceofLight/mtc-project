package com.soez.mtc.commentlike.repository;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.commentlike.entity.CommentLikeEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {
    CommentLikeEntity findCommentLikeEntityByCommentLikeCommentIndexAndCommentLikeUserIndex(CommentEntity commentEntity, UserEntity userEntity);
    Long countByCommentLikeCommentIndexAndCommentLikeValuate(CommentEntity commentEntity, Integer commentLikeValuate);

}

package com.soez.mtc.comment.repository;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByCommentUserIndex(UserEntity commentUserIndex);
    List<CommentEntity> findAllByCommentArticleIndex(ArticleEntity commentArticleIndex);
    @Transactional
    void deleteByCommentIndex(Long commentIndex);
    CommentEntity findCommentEntityByCommentIndex(Long commentIndex);
}
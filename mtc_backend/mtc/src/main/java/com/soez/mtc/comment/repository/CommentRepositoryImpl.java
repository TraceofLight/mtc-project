package com.soez.mtc.comment.repository;

import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.commentlike.repository.CommentLikeJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{
    @Autowired
    private CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public CommentViewDto getCommentViewDtoByCommentEntity(CommentEntity commentEntity) {
        UserEntity userEntity=commentEntity.getCommentUserIndex();

        Long goodCount=commentLikeJpaRepository.countByCommentLikeCommentIndexAndCommentLikeValuate(commentEntity,1);
        Long badCount=commentLikeJpaRepository.countByCommentLikeCommentIndexAndCommentLikeValuate(commentEntity,-1);



        return CommentViewDto.builder()
                .commentUserIndex(userEntity.getUserIndex())
                .commentIndex(commentEntity.getCommentIndex())
                .commentRegistTime(commentEntity.getCommentRegistTime())
                .commentUserNickname(userEntity.getUserNickname())
                .commentContent(commentEntity.getCommentContent())
                .commentArticleIndex(commentEntity.getCommentArticleIndex().getArticleIndex())
                .good(goodCount)
                .bad(badCount)
                .build();
    }
}

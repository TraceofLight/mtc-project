package com.soez.mtc.reply.repository;

import com.soez.mtc.comment.repository.CommentJpaRepository;
import com.soez.mtc.reply.dto.ReplyViewDto;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.replylike.repository.ReplyLikeJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository{
    @Autowired
    private ReplyJpaRepository replyJpaRepository;
    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ReplyLikeJpaRepository replyLikeJpaRepository;


    @Override
    public ReplyEntity readReplyEntityByReplyIndex(Long replyIndex) {
        return replyJpaRepository.findByReplyIndex(replyIndex);
    }

    @Override
    public List<ReplyEntity> readReplyEntitiesByCommentIndex(Long commentIndex) {
        return replyJpaRepository.findAllByReplyCommentIndex(commentJpaRepository.findCommentEntityByCommentIndex(commentIndex));
    }

    @Override
    public List<ReplyEntity> readReplyEntitiesByUserIndex(Long userIndex) {
        return replyJpaRepository.findAllByReplyUserIndex(userJpaRepository.findUserEntityByUserIndex(userIndex));
    }

    @Override
    public ReplyViewDto getReplyViewDtoByReplyEntity(ReplyEntity replyEntity) {
//        UserEntity userEntity=replyEntity.getReplyUserIndex();
//        Long commentIndex=replyEntity.getReplyCommentIndex().getCommentIndex();

        Long goodCount=replyLikeJpaRepository.countByReplyLikeReplyIndexAndReplyLikeValuate(replyEntity, 1);
        Long badCount=replyLikeJpaRepository.countByReplyLikeReplyIndexAndReplyLikeValuate(replyEntity, -1);

        ReplyViewDto replyViewDto= ReplyViewDto.builder()
                .replyIndex(replyEntity.getReplyIndex())
                .replyUserNickName(replyEntity.getReplyUserIndex().getUserNickname())
                .replyUserIndex(replyEntity.getReplyUserIndex().getUserIndex())
                .replyCommentIndex(replyEntity.getReplyCommentIndex().getCommentIndex())
                .replyRegistTime(replyEntity.getReplyRegistTime())
                .replyContent(replyEntity.getReplyContent())
                .good(goodCount)
                .bad(badCount)
                .build();

        return replyViewDto;
    }
}

package com.soez.mtc.replylike.repository;

import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.replylike.entity.ReplyLikeEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeJpaRepository extends JpaRepository<ReplyLikeEntity, Long> {
    ReplyLikeEntity findReplyLikeEntityByReplyLikeUserIndexAndReplyLikeReplyIndex(UserEntity userEntity, ReplyEntity replyEntity);
    Long countByReplyLikeReplyIndexAndReplyLikeValuate(ReplyEntity replyEntity,Integer replyLikeValuate);

}

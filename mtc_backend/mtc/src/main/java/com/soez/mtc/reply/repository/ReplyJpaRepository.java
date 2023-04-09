package com.soez.mtc.reply.repository;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ReplyJpaRepository extends JpaRepository<ReplyEntity,Long> {

    ReplyEntity findByReplyIndex(Long replyIndex);
    List<ReplyEntity> findAllByReplyCommentIndex(CommentEntity replyCommentIndex);
    List<ReplyEntity> findAllByReplyUserIndex(UserEntity userIndex);

    @Transactional
    void deleteByReplyIndex(Long replyIndex);
}

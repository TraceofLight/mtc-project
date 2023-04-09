package com.soez.mtc.reply.repository;

import com.soez.mtc.reply.dto.ReplyViewDto;
import com.soez.mtc.reply.entity.ReplyEntity;

import java.util.List;

public interface ReplyRepository {
    ReplyEntity readReplyEntityByReplyIndex(Long replyIndex);
    List<ReplyEntity> readReplyEntitiesByCommentIndex(Long commentIndex);
    List<ReplyEntity> readReplyEntitiesByUserIndex(Long userIndex);
    ReplyViewDto getReplyViewDtoByReplyEntity(ReplyEntity replyEntity);
}

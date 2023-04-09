package com.soez.mtc.comment.repository;

import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;

public interface CommentRepository {
    CommentViewDto getCommentViewDtoByCommentEntity(CommentEntity commentEntity);
}

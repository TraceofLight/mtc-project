package com.soez.mtc.comment.service;

import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.comment.dto.CommentPublishDto;
import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.commentlike.dto.CommentLikePublishDto;
import com.soez.mtc.commentlike.entity.CommentLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentService {
    CommentEntity createComment(CommentPublishDto commentPublishDto);
    CommentViewDto readCommentByCommentIndex(Long commentIndex);
    public List<CommentViewDto> readCommentByUserIndex(Long userIndex);
    public List<CommentViewDto> readCommentByArticleIndex(Long articleIndex);
    public void deleteCommentByCommentIndex(Long commentIndex);
    CommentLikeEntity createCommentLike(CommentLikePublishDto commentLikePublishDto);
    Integer readCommentLikeByUserIndexAndCommentIndex(Long userIndex, Long commentIndex);
    public Page<CommentViewDto> readCommentByArticleIndexWithPagingSortByLike(Long articleIndex, int page, int size);
    public Page<CommentViewDto> readCommentByArticleIndexWithPaging(Long articleIndex, int page, int size);
    public ArticleViewDto readArticleViewDtoByCommentIndex(Long commentIndex);
}

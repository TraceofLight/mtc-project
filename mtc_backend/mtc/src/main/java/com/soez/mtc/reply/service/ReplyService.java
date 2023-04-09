package com.soez.mtc.reply.service;

import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.comment.dto.CommentViewDto;
import com.soez.mtc.reply.dto.ReplyPublishDto;
import com.soez.mtc.reply.dto.ReplyViewDto;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.replylike.dto.ReplyLikePublishDto;
import com.soez.mtc.replylike.entity.ReplyLikeEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReplyService {
    ReplyEntity createReply(ReplyPublishDto replyPublishDto);

    ReplyViewDto readReplyByReplyIndex(Long replyIndex);

    List<ReplyViewDto> readReplyByCommentIndex(Long commentIndex);

    List<ReplyViewDto> readReplyByUserIndex(Long userIndex);

    void deleteReplyByReplyIndex(Long replyIndex);

    ReplyLikeEntity createReplyLike(ReplyLikePublishDto replyLikePublishDto);

    Integer findReplyValuateByReplyIndexAndUserIndex(Long replyIndex, Long userIndex);

    Page<ReplyViewDto> readReplyByCommentIndexWithPaging(Long commentIndex, int page, int size);

    ArticleViewDto findArticleViewDtoByReplyIndex(Long replyIndex);
}

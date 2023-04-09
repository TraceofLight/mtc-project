package com.soez.mtc.commentlike.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentLikePublishDto {
    private Long commentLikeCommentIndex;
    private Long commentLikeUserIndex;
    private Integer commentLikeValuate;
    @Builder
    public CommentLikePublishDto(Long commentLikeCommentIndex, Long commentLikeUserIndex, Integer commentLikeValuate){
        this.commentLikeCommentIndex=commentLikeCommentIndex;
        this.commentLikeUserIndex=commentLikeUserIndex;
        this.commentLikeValuate=commentLikeValuate;
    }
}

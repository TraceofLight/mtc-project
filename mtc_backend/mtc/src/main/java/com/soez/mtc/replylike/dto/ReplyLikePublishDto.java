package com.soez.mtc.replylike.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReplyLikePublishDto {
    private Long replyLikeReplyIndex;

    private Long replyLikeUserIndex;

    private Integer replyLikeValuate;

    @Builder
    public ReplyLikePublishDto(Long replyLikeReplyIndex, Long replyLikeUserIndex, Integer replyLikeValuate){
        this.replyLikeReplyIndex=replyLikeReplyIndex;
        this.replyLikeUserIndex=replyLikeUserIndex;
        this.replyLikeValuate=replyLikeValuate;
    }
}

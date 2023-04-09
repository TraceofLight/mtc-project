package com.soez.mtc.reply.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ReplyViewDto {
    private Long replyIndex;
    private Long replyUserIndex;
    private String replyUserNickName;
    private String replyUserPictureSource;
    private Long replyCommentIndex;
    private String replyContent;
    private LocalDateTime replyRegistTime;
    private Long good;
    private Long bad;

    @Builder
    public ReplyViewDto(Long replyIndex,String replyUserNickName, String replyUserPictureSource, Long replyUserIndex, Long replyCommentIndex, String replyContent, LocalDateTime replyRegistTime, Long good, Long bad){
        this.replyIndex=replyIndex;
        this.replyUserNickName=replyUserNickName;
        this.replyUserPictureSource =replyUserPictureSource;
        this.replyUserIndex=replyUserIndex;
        this.replyCommentIndex=replyCommentIndex;
        this.replyContent=replyContent;
        this.replyRegistTime=replyRegistTime;
        this.good= good;
        this.bad= bad;
    }
}

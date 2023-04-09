package com.soez.mtc.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentViewDto {
    private Long commentIndex;
    private String commentUserNickname;
    private String commentUserPictureSource;
    private Long commentUserIndex;
    private Long commentArticleIndex;
    private String commentContent;
    private LocalDateTime commentRegistTime;
    private Long good;
    private Long bad;

    @Builder
    public CommentViewDto(Long commentIndex, String commentUserNickname, String commentUserPictureSource,Long commentUserIndex, String commentContent, Long commentArticleIndex, LocalDateTime commentRegistTime,Long good,Long bad){
        this.commentIndex=commentIndex;
        this.commentUserNickname=commentUserNickname;
        this.commentUserPictureSource = commentUserPictureSource;
        this.commentContent=commentContent;
        this.commentRegistTime=commentRegistTime;
        this.commentArticleIndex=commentArticleIndex;
        this.commentUserIndex=commentUserIndex;
        this.good=good;
        this.bad=bad;
    }
}

package com.soez.mtc.comment.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CommentPublishDto {
    private Long commentUserIndex;
    private Long commentArticleIndex;
    private String commentContent;
}


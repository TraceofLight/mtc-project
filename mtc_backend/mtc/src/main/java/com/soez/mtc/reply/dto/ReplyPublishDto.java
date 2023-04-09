package com.soez.mtc.reply.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReplyPublishDto {
    //publish dto에 쓴게 스웨거에서 입력하는 값이 된다.
    //그래서 원래 localdatetime이 있었는데 없애고, 그냥 빌더에서 LocalDatetime.now()로 받아오는 것으로 변경했다.
    private Long replyUserIndex;
    private Long replyCommentIndex;
    private String replyContent;

    @Builder
    public ReplyPublishDto(Long replyUserIndex,Long replyCommentIndex, String replyContent){
        this.replyUserIndex=replyUserIndex;
        this.replyCommentIndex=replyCommentIndex;
        this.replyContent=replyContent;
    }
}

package com.soez.mtc.replylike.entity;

import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="reply_like")

public class ReplyLikeEntity {
    @Id
    @Column(name="reply_like_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyLikeIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_like_reply_index")
    private ReplyEntity replyLikeReplyIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_like_user_index")
    private UserEntity replyLikeUserIndex;

    @Column(name="reply_like_valuate")
    private Integer replyLikeValuate;
    //0은 선택안함. 1은 좋아요 -1은 싫어요

    @Builder
    public ReplyLikeEntity(Integer replyLikeValuate, UserEntity replyLikeUserIndex, ReplyEntity replyLikeReplyIndex){
        this.replyLikeUserIndex=replyLikeUserIndex;
        this.replyLikeValuate=replyLikeValuate;
        this.replyLikeReplyIndex=replyLikeReplyIndex;
    }

}

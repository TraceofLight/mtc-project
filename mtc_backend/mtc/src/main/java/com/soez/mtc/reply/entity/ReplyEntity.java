package com.soez.mtc.reply.entity;


import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.alarm.entity.AlarmReplyEntity;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.replylike.entity.ReplyLikeEntity;
import com.soez.mtc.report.entity.ReportReplyEntity;
import com.soez.mtc.user.entity.UserEntity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="reply")
public class ReplyEntity {
    @Id
    @Column(name="reply_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_user_index")
    private UserEntity replyUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reply_comment_index")
    private CommentEntity replyCommentIndex;

    @Column(name="reply_content")
    private String replyContent;

    @Column(name="reply_regist_time")
    private LocalDateTime replyRegistTime;

    @OneToMany(mappedBy = "replyLikeReplyIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyLikeEntity> replyGoodAndBad;//

    @OneToOne(mappedBy = "alarmReplyIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private AlarmReplyEntity alarmReplyIndex;

    @OneToMany(mappedBy = "reportReplyIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportReplyEntity> reportReplyEntities;//

    @Builder
    public ReplyEntity(UserEntity replyUserIndex, CommentEntity replyCommentIndex, String replyContent, LocalDateTime replyRegistTime){
        this.replyContent=replyContent;
        this.replyRegistTime=replyRegistTime;
        this.replyUserIndex=replyUserIndex;
        this.replyCommentIndex=replyCommentIndex;
        this.replyGoodAndBad=new ArrayList<>();
        this.reportReplyEntities=new ArrayList<>();
    }

}

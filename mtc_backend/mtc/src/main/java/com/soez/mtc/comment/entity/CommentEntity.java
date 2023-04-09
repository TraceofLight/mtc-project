package com.soez.mtc.comment.entity;
import com.soez.mtc.alarm.entity.AlarmCommentEntity;
import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.alarm.entity.AlarmFollowEntity;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.commentlike.entity.CommentLikeEntity;
import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.report.entity.ReportCommentEntity;
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
@Table(name="comment")


public class CommentEntity {
    @Id
    @Column(name="comment_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentIndex;
    //아이덴티티 자동 증가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_user_index")
    private UserEntity commentUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_article_index")
    private ArticleEntity commentArticleIndex;

    @Column(name="comment_content")
    private String commentContent;

    @Column(name="comment_regist_time")
    private LocalDateTime commentRegistTime;

    @OneToMany(mappedBy = "replyCommentIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyEntity> commentReplyList;//


    @OneToMany(mappedBy = "commentLikeCommentIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLikeEntity> commentCommentLikeList;//

    @OneToOne(mappedBy = "alarmCommentIndex", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AlarmCommentEntity alarmCommentIndex;

    @OneToMany(mappedBy = "reportCommentIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportCommentEntity> reportCommentEntities;//


    @Builder
    public CommentEntity(UserEntity commentUserIndex, ArticleEntity commentArticleIndex, String commentContent, LocalDateTime commentRegistTime){
        this.commentUserIndex=commentUserIndex;
        this.commentArticleIndex=commentArticleIndex;
        this.commentContent=commentContent;
        this.commentRegistTime=commentRegistTime;
        this.commentReplyList=new ArrayList<>();
        this.commentCommentLikeList=new ArrayList<>();
        this.reportCommentEntities=new ArrayList<>();
    }

}

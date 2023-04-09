package com.soez.mtc.article.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soez.mtc.alarm.entity.AlarmArticleEntity;
import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.hashtagging.entity.HashtaggingEntity;
import com.soez.mtc.report.entity.ReportArticleEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="article")
@Getter
@Setter
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_user_index")    // id
    private UserEntity articleUserIndex;

    private String articleTitle;

    private String articlePictureSource = "default source";

    private int articleHit;

    private int articleMaxGood;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime articleRegistTime;

    private boolean articleVisible;

    @OneToMany(mappedBy = "hashtaggingArticleIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashtaggingEntity> hashtaggingEntities;

    @OneToMany(mappedBy = "commentArticleIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> commentEntities;

    @OneToMany(mappedBy = "evaluateArticleIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluateEntity> evaluateEntities;

    @OneToMany(mappedBy = "reportArticleIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportArticleEntity> reportArticleEntities;

    @OneToMany(mappedBy = "alarmArticleIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlarmArticleEntity> alarmArticleEntities;

    @Builder
    public ArticleEntity(UserEntity articleUserIndex, String articleTitle, String articlePictureSource, int articleMaxGood, boolean articleVisible) {
        this.articleUserIndex = articleUserIndex;
        this.articleTitle = articleTitle;
        this.articleHit = 0;
        this.articleRegistTime = LocalDateTime.now();
        this.articleVisible = articleVisible;
        this.articlePictureSource = articlePictureSource;
        this.articleMaxGood = articleMaxGood;
        this.hashtaggingEntities = new ArrayList<>();
        this.commentEntities = new ArrayList<>();
        this.evaluateEntities = new ArrayList<>();
        this.reportArticleEntities = new ArrayList<>();
        this.alarmArticleEntities = new ArrayList<>();
    }
}

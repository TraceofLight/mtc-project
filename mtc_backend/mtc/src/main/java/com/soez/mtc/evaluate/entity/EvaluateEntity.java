package com.soez.mtc.evaluate.entity;

import com.soez.mtc.alarm.entity.AlarmEntity;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluate")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EvaluateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluateIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluate_user_index")
    private UserEntity evaluateUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluate_article_index")
    private ArticleEntity evaluateArticleIndex; 

    @Enumerated(EnumType.STRING)
    private EvaluateValue evaluateValue;

    private LocalDateTime evaluateDate;

    @Builder
    public EvaluateEntity(Long evaluateIndex, UserEntity evaluateUserIndex, ArticleEntity evaluateArticleIndex, EvaluateValue evaluateValue) {
        this.evaluateIndex = evaluateIndex;
        this.evaluateUserIndex = evaluateUserIndex;
        this.evaluateArticleIndex = evaluateArticleIndex;
        this.evaluateValue = evaluateValue;
        this.evaluateDate = LocalDateTime.now();
    }
}

package com.soez.mtc.recent.entity;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "recent_view")
@Getter
@Setter
@NoArgsConstructor
public class RecentViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recentViewIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recent_view_user_index")
    private UserEntity recentViewUserIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recent_view_article_index")
    private ArticleEntity recentViewArticleIndex;

    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime recentViewTime;

    @Builder
    public RecentViewEntity(Long recentViewIndex, UserEntity recentViewUserIndex, ArticleEntity recentViewArticleIndex, LocalDateTime recentViewTime) {
        this.recentViewIndex = recentViewIndex;
        this.recentViewUserIndex = recentViewUserIndex;
        this.recentViewArticleIndex = recentViewArticleIndex;
        this.recentViewTime = recentViewTime;
    }
}

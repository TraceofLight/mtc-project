package com.soez.mtc.hashtagging.entity;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.hashtag.entity.HashtagEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "hashtagging")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HashtaggingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long hashtaggingIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtagging_article_index")
    private ArticleEntity hashtaggingArticleIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtagging_tagname")
    private HashtagEntity hashtaggingTagname;

    private void setHashtaggingArticleIndex(ArticleEntity articleEntity){
        this.hashtaggingArticleIndex = articleEntity;
        articleEntity.getHashtaggingEntities().add(this);
    }

    @Builder
    public HashtaggingEntity(Long hashtaggingIndex, ArticleEntity hashtaggingArticleIndex, HashtagEntity hashtaggingTagname) {
        this.hashtaggingIndex = hashtaggingIndex;
        this.hashtaggingArticleIndex = hashtaggingArticleIndex;
        this.hashtaggingTagname = hashtaggingTagname;
    }
}

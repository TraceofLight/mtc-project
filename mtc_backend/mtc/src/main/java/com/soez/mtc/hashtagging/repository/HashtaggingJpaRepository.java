package com.soez.mtc.hashtagging.repository;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.hashtag.entity.HashtagEntity;
import com.soez.mtc.hashtagging.entity.HashtaggingEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtaggingJpaRepository extends JpaRepository<HashtaggingEntity, Long> {
    List<HashtaggingEntity> findHashtaggingEntitiesByHashtaggingArticleIndex(ArticleEntity articleEntity);

    @Query(value = "select h from HashtaggingEntity h join ArticleEntity a on h.hashtaggingArticleIndex = a left join BlockEntity b on b.blockTargetIndex = a.articleUserIndex where h.hashtaggingTagname = :hashtag and (b.blockUserIndex is null or b.blockUserIndex != :userIndex)")
    Page<HashtaggingEntity> findHashtaggingEntitiesByHashtaggingTagname(@Param("hashtag") HashtagEntity hashtagEntity, @Param("userIndex") UserEntity userEntity, Pageable pageable);

    void deleteHashtaggingEntitiesByHashtaggingArticleIndex(ArticleEntity articleEntity);
}

package com.soez.mtc.recent.repository;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.recent.entity.RecentViewEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentViewJpaRepository extends JpaRepository<RecentViewEntity, Long> {

    RecentViewEntity findRecentViewEntityByRecentViewUserIndexAndRecentViewArticleIndex(UserEntity userEntity, ArticleEntity articleEntity);

}

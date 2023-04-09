package com.soez.mtc.article.repository;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {

    /**
     * 게시물 인덱스로 게시물 조회
     * @param articleIndex
     * @return
     */
    ArticleEntity findArticleEntityByArticleIndex(Long articleIndex);

    /**
     * 제목으로 게시물 목록 검색
     * @param keyword
     * @return
     */
    @Query(value = "select a from ArticleEntity a where a.articleTitle like %:keyword% and a.articleVisible = true order by a.articleRegistTime desc")
    Page<ArticleEntity> findArticleEntitiesByArticleTitle(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 내가 올린 게시물 목록 조회
     * @param articleUserIndex
     * @return
     */
    Page<ArticleEntity> findAllByArticleUserIndexOrderByArticleRegistTimeDesc(UserEntity articleUserIndex, Pageable pageable);

    /**
     * 유저의 공개 게시물 목록 조회
     * @param
     * @param visible
     * @return
     */
    Page<ArticleEntity> findAllByArticleUserIndexAndArticleVisibleOrderByArticleRegistTimeDesc(UserEntity userEntity, boolean visible, Pageable pageable);

    /**
     * 최근 본 게시물 목록 조회
     * @param userEntity
     * @return
     */
    @Query(value = "select a from RecentViewEntity rv left join ArticleEntity a on rv.recentViewArticleIndex = a left join BlockEntity b on a.articleUserIndex = b.blockTargetIndex where rv.recentViewUserIndex = :userIndex and a.articleVisible = true and (rv.recentViewUserIndex != b.blockUserIndex or b.blockUserIndex is null) order by rv.recentViewTime desc")
    Page<ArticleEntity> findRecentArticleEntities(@Param("userIndex") UserEntity userEntity, Pageable pageable);

    /**
     * 내가 평가한 게시물 목록 조회
     * @param userEntity
     * @return
     */
    @Query(value = "select distinct e from EvaluateEntity e left join ArticleEntity a on e.evaluateArticleIndex = a left join BlockEntity b on a.articleUserIndex=b.blockTargetIndex where e.evaluateUserIndex = :userIndex and e.evaluateValue != 'NONE' order by e.evaluateDate desc")
    Page<EvaluateEntity> findEvaluateArticleEntities(@Param("userIndex") UserEntity userEntity, Pageable pageable);

    @Query(value = "select a from ArticleEntity a left join FollowEntity f on a.articleUserIndex = f.followTargetIndex where f.followUserIndex = :userIndex and a.articleVisible = true order by a.articleRegistTime desc")
    Page<ArticleEntity> findFollowingArticleEntities(@Param("userIndex") UserEntity userEntity, Pageable pageable);

    @Query(value = "select a from ArticleEntity a left join EvaluateEntity e on a = e.evaluateArticleIndex left join BlockEntity b on a.articleUserIndex = b.blockTargetIndex " +
            "where e.evaluateValue = 'GOOD' and a.articleVisible = true and (b.blockUserIndex is null or b.blockUserIndex != :userIndex) " +
            "group by e.evaluateArticleIndex order by count(e.evaluateArticleIndex) desc")
    Page<ArticleEntity> findArticleEntitiesOrderByGoood(@Param("userIndex") UserEntity userEntity, Pageable pageable);

    @Query(value = "select distinct a from ArticleEntity a left join BlockEntity b on a.articleUserIndex = b.blockTargetIndex where a.articleVisible = true and (b.blockUserIndex != :userIndex or b.blockUserIndex is null) order by a.articleRegistTime desc")
    Page<ArticleEntity> findArticles(@Param("userIndex") UserEntity userEntity, Pageable pageable);

    void deleteArticleEntityByArticleIndex(Long articleIndex);
}

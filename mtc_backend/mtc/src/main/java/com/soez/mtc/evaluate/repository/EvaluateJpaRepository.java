package com.soez.mtc.evaluate.repository;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.evaluate.entity.EvaluateValue;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluateJpaRepository extends JpaRepository<EvaluateEntity, Long> {

    @Override
    <S extends EvaluateEntity> S save(S entity);

    EvaluateEntity findEvaluateEntityByEvaluateUserIndexAndEvaluateArticleIndex(UserEntity userEntity, ArticleEntity articleEntity);

    int countEvaluateEntitiesByEvaluateArticleIndexAndEvaluateValue(ArticleEntity articleEntity, EvaluateValue evaluateValue);

    @Query(value = "select count(*) from EvaluateEntity e join ArticleEntity a on a = e.evaluateArticleIndex where a.articleUserIndex = :userIndex and e.evaluateValue = :evaluateValue")
    int totalCountEvaluateEntitiesByEvaluateUserIndexAndEvaluateValue(@Param("userIndex") UserEntity userEntity, @Param("evaluateValue") EvaluateValue evaluateValue);
}

package com.soez.mtc.article.repository;

import com.soez.mtc.article.dto.ArticlePublishDto;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.evaluate.entity.EvaluateEntity;
import com.soez.mtc.evaluate.entity.EvaluateValue;
import com.soez.mtc.evaluate.repository.EvaluateJpaRepository;
import com.soez.mtc.hashtagging.entity.HashtaggingEntity;
import com.soez.mtc.hashtagging.repository.HashtaggingJpaRepository;
import com.soez.mtc.user.entity.UserEntity;
import com.soez.mtc.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository{

    private final UserJpaRepository userJpaRepository;
    private final ArticleJpaRepository articleJpaRepository;
    private final EvaluateJpaRepository evaluateJpaRepository;
    private final HashtaggingJpaRepository hashtaggingJpaRepository;

    @Override
    public ArticleViewDto getArticleViewDto(Long userIndex, Long articleIndex) {
        ArticleEntity articleEntity = articleJpaRepository.findArticleEntityByArticleIndex(articleIndex);
        UserEntity userEntity = userJpaRepository.findUserEntityByUserIndex(userIndex);

        List<HashtaggingEntity> hashtaggingEntities = hashtaggingJpaRepository.findHashtaggingEntitiesByHashtaggingArticleIndex(articleEntity);
        List<String> hashtagList = new ArrayList<>();
        for(HashtaggingEntity hashtaggingEntity : hashtaggingEntities){
            hashtagList.add(hashtaggingEntity.getHashtaggingTagname().getTagname());
        }
        EvaluateEntity evaluateEntity = evaluateJpaRepository.findEvaluateEntityByEvaluateUserIndexAndEvaluateArticleIndex(userEntity, articleEntity);

        return ArticleViewDto.builder()
                .userIndex(articleEntity.getArticleUserIndex().getUserIndex())
                .userNickname(articleEntity.getArticleUserIndex().getUserNickname())
                .userPictureSource(articleEntity.getArticleUserIndex().getUserPictureSource())
                .articleIndex(articleEntity.getArticleIndex())
                .articleTitle(articleEntity.getArticleTitle())
                .articlePictureSource(articleEntity.getArticlePictureSource())
                .articleHit(articleEntity.getArticleHit())
                .hashtagList(hashtagList)
                .goodCount(evaluateJpaRepository.countEvaluateEntitiesByEvaluateArticleIndexAndEvaluateValue(articleEntity, EvaluateValue.GOOD))
                .sosoCount(evaluateJpaRepository.countEvaluateEntitiesByEvaluateArticleIndexAndEvaluateValue(articleEntity, EvaluateValue.SOSO))
                .badCount(evaluateJpaRepository.countEvaluateEntitiesByEvaluateArticleIndexAndEvaluateValue(articleEntity, EvaluateValue.BAD))
                .evaluateValue((evaluateEntity == null) ? EvaluateValue.NONE : evaluateEntity.getEvaluateValue())
                .build();
    }
}

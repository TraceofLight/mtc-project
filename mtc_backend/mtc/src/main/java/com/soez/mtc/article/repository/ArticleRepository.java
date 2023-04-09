package com.soez.mtc.article.repository;

import com.soez.mtc.article.dto.ArticlePublishDto;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.entity.ArticleEntity;

public interface ArticleRepository {
    ArticleViewDto getArticleViewDto(Long userIndex, Long articleIndex);
}

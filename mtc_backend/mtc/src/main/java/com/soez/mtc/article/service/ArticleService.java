package com.soez.mtc.article.service;

import com.soez.mtc.article.dto.ArticlePublishDto;
import com.soez.mtc.article.dto.ArticleViewDto;
import com.soez.mtc.article.dto.ArticleViewListDto;
import com.soez.mtc.article.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ArticleService {
    ArticleViewDto createArticle(ArticlePublishDto articlePublishDto, String url) throws IOException, URISyntaxException;

    ArticleViewDto readArticleByArticleIndex(Long userIndex, Long articleIndex);

    ArticleViewListDto readArticlesByUserIndex(Long writer, Long viewer, Pageable pageable);

    ArticleViewListDto readArticlesByArticleTitle(Long userIndex, String keyword, Pageable pageable);

    ArticleViewListDto readRecentArticles(Long userIndex, Pageable pageable);

    ArticleViewListDto readEvaluateArticles(Long userIndex, Pageable pageable);

    ArticleViewListDto readArticlesByHashtag(Long userIndex, String hashtag, Pageable pageable);

    ArticleViewListDto readFollowingArticles(Long userIndex, Pageable pageable);

    List<ArticleViewDto> readRecommendArticles(Long userIndex, String page, String size) throws URISyntaxException, IOException;

    ArticleViewListDto readHotArticles(Long userIndex, Pageable pageable);

    ArticleViewListDto readArticles(Long userIndex, Pageable pageable);

    boolean deleteArticle(Long userIndex, Long articleIndex) throws URISyntaxException, IOException;


}

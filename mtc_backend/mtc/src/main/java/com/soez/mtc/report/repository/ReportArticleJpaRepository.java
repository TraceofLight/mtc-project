package com.soez.mtc.report.repository;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.report.entity.ReportArticleEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportArticleJpaRepository extends JpaRepository<ReportArticleEntity,Long> {
    List<ReportArticleEntity> findAllByReportArticleIndex(ArticleEntity articleIndex);

    ReportArticleEntity findByReportArticleIndexAndReportUser(ArticleEntity articleEntity, UserEntity userEntity);

    Long countByReportArticleIndex(ArticleEntity articleIndex);
}

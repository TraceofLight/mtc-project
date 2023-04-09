package com.soez.mtc.report.entity;

import com.soez.mtc.article.entity.ArticleEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("A")
@Setter
@Getter
@NoArgsConstructor
public class ReportArticleEntity extends ReportEntity{
    @JoinColumn(name="report_article_index")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity reportArticleIndex;

    @Builder
    public ReportArticleEntity(String reportContent, UserEntity reportUserIndex, ArticleEntity reportArticleIndex){
        this.setReportContent(reportContent);
        this.setReportUser(reportUserIndex);
        this.reportArticleIndex=reportArticleIndex;
    }
}

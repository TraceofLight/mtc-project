package com.soez.mtc.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportRegistDto {
    private Long reportUserIndex;

    private String reportDtype;

    private Long reportReplyIndex;

    private Long reportArticleIndex;

    private Long reportCommentIndex;

    private String reportConetent;

    @Builder
    public ReportRegistDto(Long reportUserIndex, String reportDtype, Long reportArticleIndex, Long reportCommentIndex, Long reportReplyIndex,String reportConetent){
        this.reportUserIndex=reportUserIndex;
        this.reportDtype=reportDtype;
        this.reportReplyIndex=reportReplyIndex;
        this.reportArticleIndex=reportArticleIndex;
        this.reportCommentIndex=reportCommentIndex;
        this.reportConetent=reportConetent;
    }

}

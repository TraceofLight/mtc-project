package com.soez.mtc.report.entity;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@DiscriminatorValue("C")
@Setter
@Getter
@NoArgsConstructor
public class ReportCommentEntity extends ReportEntity{
    @JoinColumn(name="report_comment_index")
    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity reportCommentIndex;

    @Builder
    public ReportCommentEntity(String reportContent, UserEntity reportUserIndex, CommentEntity reportCommentIndex){
        this.setReportContent(reportContent);
        this.setReportUser(reportUserIndex);
        this.reportCommentIndex=reportCommentIndex;
    }
}

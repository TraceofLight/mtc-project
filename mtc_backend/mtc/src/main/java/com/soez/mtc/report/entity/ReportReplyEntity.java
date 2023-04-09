package com.soez.mtc.report.entity;

import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("R")
@Setter
@Getter
@NoArgsConstructor
public class ReportReplyEntity extends ReportEntity{
    @JoinColumn(name="report_reply_index")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReplyEntity reportReplyIndex;

    @Builder
    public ReportReplyEntity(String reportContent, UserEntity reportUserIndex, ReplyEntity reportReplyIndex){
        this.setReportContent(reportContent);
        this.setReportUser(reportUserIndex);
        this.reportReplyIndex=reportReplyIndex;
    }
}

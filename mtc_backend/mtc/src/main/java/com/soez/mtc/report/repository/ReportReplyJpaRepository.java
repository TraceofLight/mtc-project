package com.soez.mtc.report.repository;

import com.soez.mtc.reply.entity.ReplyEntity;
import com.soez.mtc.report.entity.ReportReplyEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportReplyJpaRepository extends JpaRepository<ReportReplyEntity,Long> {
    List<ReportReplyEntity> findAllByReportReplyIndex(ReplyEntity replyIndex);

    ReportReplyEntity findByReportReplyIndexAndReportUser(ReplyEntity replyEntity, UserEntity userEntity);

    Long countByReportReplyIndex(ReplyEntity replyIndex);
}

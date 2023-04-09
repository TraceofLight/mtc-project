package com.soez.mtc.report.repository;

import com.soez.mtc.comment.entity.CommentEntity;
import com.soez.mtc.report.entity.ReportCommentEntity;
import com.soez.mtc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportCommentJpaRepository extends JpaRepository<ReportCommentEntity, Long> {
    List<ReportCommentEntity> findAllByReportCommentIndex(CommentEntity commentIndex);

    ReportCommentEntity findByReportCommentIndexAndReportUser(CommentEntity commentEntity, UserEntity userEntity);

    Long countByReportCommentIndex(CommentEntity commentIndex);
}

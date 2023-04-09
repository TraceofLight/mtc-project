package com.soez.mtc.report.repository;

import com.soez.mtc.report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Long> {
    @Transactional
    void deleteByReportIndex(Long reportIndex);
}

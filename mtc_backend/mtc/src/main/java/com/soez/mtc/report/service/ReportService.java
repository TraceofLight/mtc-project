package com.soez.mtc.report.service;

import com.soez.mtc.report.dto.ReportRegistDto;
import com.soez.mtc.report.dto.ReportViewDto;
import com.soez.mtc.report.entity.ReportEntity;

import java.util.List;

public interface ReportService {
    List<ReportViewDto> readReportByReportIndex(Long reportIndex);

    List<ReportViewDto> readReportByUserIndex(Long userIndex);

    ReportEntity createReport(ReportRegistDto reportRegistDto);
}

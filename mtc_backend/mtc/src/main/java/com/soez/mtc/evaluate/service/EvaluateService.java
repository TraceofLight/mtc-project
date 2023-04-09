package com.soez.mtc.evaluate.service;

import com.soez.mtc.evaluate.dto.EvaluateDto;
import com.soez.mtc.evaluate.dto.EvaluateStatisticsDto;
import com.soez.mtc.evaluate.entity.EvaluateEntity;

public interface EvaluateService {

    boolean createEvaluate(EvaluateDto evaluateDto);

    EvaluateStatisticsDto getEvaluateStatistics(Long userIndex);
}

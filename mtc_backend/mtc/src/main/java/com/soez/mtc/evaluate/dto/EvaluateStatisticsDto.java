package com.soez.mtc.evaluate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EvaluateStatisticsDto {
    Integer goodCount;
    Integer sosoCount;
    Integer badCount;
}

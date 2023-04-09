package com.soez.mtc.evaluate.dto;

import com.soez.mtc.evaluate.entity.EvaluateValue;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluateDto {

    private Long user;
    private Long article;
    private EvaluateValue evaluateType;
}

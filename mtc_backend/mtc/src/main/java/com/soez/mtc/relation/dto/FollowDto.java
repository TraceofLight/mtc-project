package com.soez.mtc.relation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FollowDto {

    private long followIndex;
    private long followUserIndex;
    private long followTargetIndex;
}

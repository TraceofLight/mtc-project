package com.soez.mtc.relation.dto;

import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BlockDto {

    private long blockIndex;

    private long blockUserIndex;

    private long blockTargetIndex;
}

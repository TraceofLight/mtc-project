package com.soez.mtc.relation.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockUserDto {

    private Long blockIndex;
    private Long userIndex;
    private String userNickname;

    private String userPictureSource;

}

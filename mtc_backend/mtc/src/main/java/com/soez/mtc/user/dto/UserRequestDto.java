package com.soez.mtc.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {
    private Long userIndex;
    private String userUid;
}
